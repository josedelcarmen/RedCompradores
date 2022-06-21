package pe.gob.osce.redcompradores.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.osce.redcompradores.chatroom.entity.TblComunicacionArchivo;
import pe.gob.osce.redcompradores.publicacion.emtity.TblArchivo;
import pe.gob.osce.redcompradores.security.entity.MprUsuario;
import pe.gob.osce.redcompradores.util.Utility;

/**
 * Servicio encargado de adjuntar los archivos adjuntos de las publicaciones,
 * chatroom y foto de perfil en sus resepctivos repositorios
 * 
 * <ul>
 * <li>[2021] [OSCE - Red de Compradores].</li>
 * </ul>
 * 
 * @author [Fernando Cuadros] o [DELTA]
 * @version [1.0]
 * @since [2021]
 * 
 */

@Component
public class FileStorageService {

	private static final AuditoriaService logger = new AuditoriaService(FileStorageService.class);

	@Value("${rc.package.usuario}")
	private String PACKAGE_USUARIO;
	
	@Value("${rc.package.publicacion}")
	private String PACKAGE_PUBLICACION;
	
	@Value("${rc.package.chatroom}")
	private String PACKAGE_CHATROOM;
	
	/**
	 * Metodo encargado de cargar un archivo al repositorio de publicaciones
	 * 
	 * @param archivo
	 * @param tema
	 * @param idPublicacion
	 * @param tblArchivo
	 * @return boolean
	 * @throws IOException 
	 */
	public void subirArchivo(MultipartFile archivo, String tema, String idPublicacion, TblArchivo tblArchivo) throws IOException {
		logger.debug("FileStorageService::subirArchivo:: " + tema + " : " + idPublicacion);

		String extension = "";
		String fechadirectorio = Utility.generaFechaDirectorio();
		String rutadestino = PACKAGE_PUBLICACION + File.separator;
		FileOutputStream fout = null;
		try {
			extension = archivo.getOriginalFilename().split("\\.")[1];
			File directorio = new File(rutadestino + fechadirectorio);
			if (!directorio.exists()) {
				directorio.mkdir();
			}
			File subdirectorio = new File(rutadestino + fechadirectorio + File.separator + tema);
			if (!subdirectorio.exists()) {
				subdirectorio.mkdir();
			}
			File documento = new File(rutadestino + fechadirectorio + File.separator + tema + File.separator
					+ idPublicacion + "-" + System.currentTimeMillis() + "." + extension);
			
			logger.debug("FileStorageService::subirArchivo::Nombre de archivo a subir: " + documento.getParent());
			if (documento.createNewFile()) {
				fout = new FileOutputStream(documento);
				fout.write(archivo.getBytes());
				fout.close();
				logger.debug("FileStorageService::subirArchivo::El archivo se cargo correctamente.");
				
				tblArchivo.setCValNomOriArchivo(archivo.getOriginalFilename());
				tblArchivo.setCValNomArchivo(documento.getName());
				tblArchivo.setCValRutaArchivo(documento.getAbsolutePath());
				
				logger.debug("FileStorageService::subirArchivo::fin");

			}
			else {
				logger.debug("FileStorageService::subirArchivo::No se pudo crear el archivo: " + idPublicacion + "-" + System.currentTimeMillis() + "." + extension);

			}

		} catch (Exception e) {
			logger.error("FileStorageService::subirArchivo::Error en subirArchivo: " + e.getMessage(), e);			
		} finally {
			if (fout != null)
				fout.close();
		}

	}

	/**
	 * Metodo encargado de cargar un archivo al repositorio de chatroom
	 * 
	 * @param archivo
	 * @param tblComunicacionArchivo
	 * @return boolean
	 * @throws IOException 
	 */
	public void subirArchivoChatRoom(MultipartFile archivo, TblComunicacionArchivo tblComunicacionArchivo) throws IOException {
		logger.debug("FileStorageService::subirArchivoChatRoom::inicio");

		String extension = "";
		String rutadestino = PACKAGE_CHATROOM + File.separator;
		FileOutputStream fout = null;
		try {
			extension = archivo.getOriginalFilename().split("\\.")[1];

			File directorio = new File(rutadestino);
			if (!directorio.exists()) {
				directorio.mkdir();
			}

			String nuevoNombreFile = "file-" + System.currentTimeMillis() + "." + extension;
			File documento = new File(rutadestino + File.separator + nuevoNombreFile);

			logger.debug("FileStorageService::subirArchivoChatRoom::Nombre de archivo a subir: " + documento.getParent());
			if (documento.createNewFile()) {
				fout = new FileOutputStream(documento);
				fout.write(archivo.getBytes());
				fout.close();
				logger.debug("FileStorageService::subirArchivoChatRoom::El archivo se cargo correctamente.");
				
				tblComunicacionArchivo.setCValNomArchivo(nuevoNombreFile);
				tblComunicacionArchivo.setCValNomOriArchivo(archivo.getOriginalFilename());
				tblComunicacionArchivo.setCValRutaArchivo(documento.getAbsolutePath());

				logger.debug("FileStorageService::subirArchivoChatRoom::fin");
				
			}
			else {
				logger.debug("FileStorageService::subirArchivoChatRoom::No se pudo crear el archivo: " + nuevoNombreFile);

			}
		} catch (Exception e) {
			logger.error("FileStorageService::subirArchivoChatRoom::Error en subirArchivo: " + e.getMessage());
		} finally {
			if (fout != null)
				fout.close();
		}

	}

	/**
	 * Metodo encargado de cargar un archivo al repositorio de usuarios
	 * 
	 * @param archivo
	 * @param mprUsuario
	 * @return boolean
	 * @throws IOException 
	 */
	public void subirArchivoUsuario(MultipartFile archivo, MprUsuario mprUsuario) throws IOException {
		logger.debug("FileStorageService::subirArchivoUsuario::inicio");

		String extension = "";
		String rutadestino = PACKAGE_USUARIO + File.separator;
		FileOutputStream fout = null;
		
		try {
			extension = archivo.getOriginalFilename().split("\\.")[1];

			File directorio = new File(rutadestino);
			if (!directorio.exists()) {
				directorio.mkdir();
			}

			String nuevoNombreFile = "usuario-file-" + mprUsuario.getNIdUsuario() + "." + extension;
			String pathFile = rutadestino + File.separator + nuevoNombreFile;
			File documento = new File(pathFile);

			if (documento.exists()) {
				boolean deleted = Files.deleteIfExists(Paths.get(pathFile));
				if (deleted)
					logger.debug("FileStorageService::subirArchivoUsuario::Archivo eliminado correctamente");
			}
			logger.debug("FileStorageService::subirArchivoUsuario::Nombre de archivo a subir: " + documento.getParent());
			
			if (documento.createNewFile()) {
				fout = new FileOutputStream(documento);
				fout.write(archivo.getBytes());
				fout.close();
				logger.debug("FileStorageService::subirArchivoUsuario::El archivo se cargo correctamente.");
				mprUsuario.setCDirFoto(nuevoNombreFile);
				logger.debug("FileStorageService::subirArchivoUsuario::fin");
				
			}
			else {
				logger.debug("FileStorageService::subirArchivoUsuario::No se pudo crear el archivo: " + nuevoNombreFile);
			}
			
		} catch (Exception e) {
			logger.error("FileStorageService::subirArchivoUsuario::Error en subirArchivo: " + e.getMessage(), e);
		} finally {
			if (fout != null)
				fout.close();
		}
	}
	
	/**
	 * Metodo encargado de devolver un arreglo de byte de un archivo
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public byte[] contentOf(String fileName) throws Exception {
		return Files.readAllBytes(Paths.get(fileName));
	}

	/**
	 * Metodo encargado de obtener Resource de la foto de perfil
	 * 
	 * @param pathFile
	 * @return Resource
	 */
	public Resource loadFileFotoPerfil(String pathFile) {
		try {
			String path = PACKAGE_USUARIO + File.separator;
			Path file = Paths.get(path + pathFile);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				String pathfileDefault = path + "usuario-file-0.png";
				Path fileDefault = Paths.get(pathfileDefault);
				Resource resourceDefault = new UrlResource(fileDefault.toUri());
				return resourceDefault;
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FileStorageService::loadFileFotoPerfil::URL mal formado para la busqueda de foto de usuario");
		}
	}

	/**
	 * Metodo encargado de devolver una cadena codificada con la foto de perfil de
	 * un usuario determinado
	 * 
	 * @param fileName
	 * @return String
	 * @throws Exception
	 */
	public String contentOfFotoPerfil(String fileName) throws Exception {
		String path = PACKAGE_USUARIO + File.separator;
		if (fileName != null) {
			String encodeImage = Base64.getEncoder().withoutPadding()
					.encodeToString(Files.readAllBytes(Paths.get(path + fileName)));
			return encodeImage;
		} else {
			String pathfileDefault = path + "usuario-file-0.png";
			String encodeImageDefault = Base64.getEncoder().withoutPadding()
					.encodeToString(Files.readAllBytes(Paths.get(pathfileDefault)));
			return encodeImageDefault;
		}
	}

	/**
	 * Metodo encargado de devolver un arreglo de byte de un archivo de chatroom
	 * 
	 * @param fileName
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] contentOfChatroom(String fileName) throws Exception {
		String file = PACKAGE_CHATROOM + File.separator + fileName;
		return Files.readAllBytes(Paths.get(file));
	}
}
