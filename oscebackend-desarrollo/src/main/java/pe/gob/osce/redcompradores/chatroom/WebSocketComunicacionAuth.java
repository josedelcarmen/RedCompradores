package pe.gob.osce.redcompradores.chatroom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import pe.gob.osce.redcompradores.security.jwt.JwtProvider;
import pe.gob.osce.redcompradores.security.service.UserDetailsServiceImpl;
import pe.gob.osce.redcompradores.util.Utility;

/**
 * Clase configuradora de seguridad para que todas las conversaciones
 * del chatroom se realicen con un token valido
* 
 * <ul>
 * <li> [2021] [OSCE - Red de Compradores].</li>
 * </ul>
 * 
* @author [Fernando Cuadros] o [DELTA]
* @version [1.0]
* @since [2021]
* 
*/
@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketComunicacionAuth implements WebSocketMessageBrokerConfigurer{
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                	List<String> autorizacion = accessor.getNativeHeader("Authorization");
                	String token = Utility.getToken(autorizacion.get(0));
                	
                	if (token != null && jwtProvider.validateToken(token)) {
        				String nombreUsuario = jwtProvider.getUsernameFromToken(token);
        				UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

        				UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userDetails, null,
        						userDetails.getAuthorities());
        				accessor.setUser(user);
        			}
                }
                return message;
            }
        });
    }
}
