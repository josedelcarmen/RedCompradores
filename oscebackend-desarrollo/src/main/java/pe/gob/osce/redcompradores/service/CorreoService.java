package pe.gob.osce.redcompradores.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Clase encargada del envio de correos
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
@Service
public class CorreoService {

	private static final AuditoriaService logger = new AuditoriaService(CorreoService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * Metodo encargado del envio de correos en formato HTML
	 * @param para
	 * @param asunto
	 * @param contenido
	 */
	public void enviarCorreoHTML(String para, String asunto, String contenido) throws MessagingException{
		logger.debug("CorreoService::enviarCorreoHTML::inicio");
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		mimeMessage.setContent(contenido, "text/html");
		helper.setText(contenido, true);
		helper.setTo(para);
		helper.setSubject(asunto);
		javaMailSender.send(mimeMessage);
		logger.debug("CorreoService::enviarCorreoHTML::Se envio el correo con exito a " + para);
		logger.debug("CorreoService::enviarCorreoHTML::fin");
	}

}
