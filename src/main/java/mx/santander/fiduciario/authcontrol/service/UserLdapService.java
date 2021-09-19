package mx.santander.fiduciario.authcontrol.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import mx.santander.fiduciario.authcontrol.dto.UserDto;
import mx.santander.fiduciario.authcontrol.dto.change.put.ChangePasswordReqDto;
import mx.santander.fiduciario.authcontrol.dto.change.put.ChangePasswordResDto;
import mx.santander.fiduciario.authcontrol.dto.enroll.post.EnrollReqDto;
import mx.santander.fiduciario.authcontrol.dto.enroll.post.EnrollResDto;
import mx.santander.fiduciario.authcontrol.dto.login.post.LoginReqDto;
import mx.santander.fiduciario.authcontrol.dto.login.post.LoginResDto;
import mx.santander.fiduciario.authcontrol.dto.user.get.FindByBucResDto;
import mx.santander.fiduciario.authcontrol.enumerations.StatusUser;
import mx.santander.fiduciario.authcontrol.exception.GeneralException;
import mx.santander.fiduciario.authcontrol.exception.PersistenDataException;
import mx.santander.fiduciario.authcontrol.exception.catalog.GeneralCatalog;
import mx.santander.fiduciario.authcontrol.exception.catalog.PersistenDataCatalog;
import mx.santander.fiduciario.authcontrol.mapper.UserLoginMapper;
import mx.santander.fiduciario.authcontrol.mapper.UserMapper;
import mx.santander.fiduciario.authcontrol.mapper.ChangePasswordMapper;
import mx.santander.fiduciario.authcontrol.mapper.EnrollMapper;
import mx.santander.fiduciario.authcontrol.mapper.FindByBucMapper;
import mx.santander.fiduciario.authcontrol.mapper.UserAttribute;
import mx.santander.fiduciario.authcontrol.model.UserModel;
import mx.santander.fiduciario.authcontrol.util.CoderUtil;
@Service
public class UserLdapService implements IUserLdapService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLdapService.class);
	
    @Autowired
    private LdapTemplate ldapTemplate;
    
    private static final String org = ",ou=people,dc=santander,dc=com";
    
    public static final String BASE_DN = "dc=santander,dc=com";
    
    /**
     * Es metodo permite hacer una consulta a los registros de LDAP y consultar por DN
     * @param dn Parametro por el cual se hace la busqueda
     * @return regresa un UserModel con los datos asociados a la consulta.
     * @throws lanza un error de PersistenData "Recurso no encontrado" al no encontrar el recurso buscado.
     */
	@Override
	public UserModel findByDn(String dn) {
		UserModel userModel = null;
		try {
			/**Se hace consulta a LDAP*/
			userModel = ldapTemplate.lookup("employeeNumber="+dn+org, new UserAttribute());
			LOGGER.info("findByDn: {}",userModel.toString());
		}catch (NameNotFoundException e) {
			LOGGER.error("No se encontro recurso, buc: {}",dn);
			throw new PersistenDataException(PersistenDataCatalog.PSID003, "Su usuario es incorrecto");
		}
		return userModel;
	}
    
	/**
	 * Este metodo permite guardar los datos de un usuario en los registros de LDAP
	 * @param userModel Usuario con datos a guardar
	 * @return regresa un UserModel con los datos guardados
	 * @throws lanza un error GeneralException "Conflicto al realizar la operación" al guardar datos en los registros de LDAP
	 */
	@Override
	public UserModel save(UserModel userModel) {
		Name dnName = buildDn(userModel.getBuc());
		Attributes userAttributes = null;
		try {
			userAttributes = buildAttributes(userModel);
			ldapTemplate.rebind(dnName, null, userAttributes);
			LOGGER.info("Exito al guardar/actualizar a LDAP, user: {}",userModel);
		}catch (Exception e) {
			LOGGER.error("Error en save user, error: {}",e.getMessage());
			throw new GeneralException(GeneralCatalog.GRAL004, "Error al guardar registro, con buc: "+userModel.getBuc());
		}
		return userModel;
	}
	
    /**
     * Este meotodo permite hacer lsa valdiacion para un logeo, donde se verifica el buc y password correctos, asi mismo hace el incremento del 
     * contador de intentos, cuando no se realiza un logueo exitoso, y cambia el estatus a BLOQUEDADO, dependiendo si se ha superado el numero
     * maximo de intentos
     * @param userDto con datos asociados al logueo
     * @return loginResDto datos asociados al codigo de la operacion
     */
	@Override
	public LoginResDto login(LoginReqDto loginReqDto) {
		UserModel userModel = null;
		LoginResDto loginResDto = null;
		userModel = this.findByDn(loginReqDto.getBuc());
		/**Obtenemos contador de logeo*/
		int counterLogin = Integer.parseInt(userModel.getAttempts());
		int counterAux = 0;
		/**Validacion de negocio,intentos de login meayor a 3, no permite login*/
		if(counterLogin >= 3) {
			loginResDto = UserLoginMapper.userToDto(userModel, "Se ha superado el numero maximo de intententos permitidos");
			return loginResDto;
		}
		/**Se agrega ultima fecha de logueo*/
		userModel.setLastConecction(new Date().toString());
		/**Validacion de negocio, coincidencia de password*/
		LOGGER.info("PASS ENVIADO(LOGIN)> {}",loginReqDto.getPassword());
		if(CoderUtil.getDecoder(userModel.getPassword()).equalsIgnoreCase(loginReqDto.getPassword())) { //userModel.getPassword().equalsIgnoreCase(userDto.getPassword())
			/**Se inicia sesion con exito*/
			counterAux = 0;	
			counterLogin = 0;
			loginResDto = UserLoginMapper.userToDto(userModel, "Ok");
			/**Bandera sesion con exito*/
			loginResDto.setSession(true);
			LOGGER.info("Login con EXITO, buc: "+userModel.getBuc());
		}else {	/**Validacion de negocio, no hay coincidencia de password*/
			//Se incrementa contador, por password incorrecto
			counterAux = 1;
			loginResDto = UserLoginMapper.userToDto(userModel, "Se ha ingresado una contraseña incorrecta");
			LOGGER.error("Error en: login, error: password incorrecto");
		}
		/**Validacion de negocio, estatus del usuario tiene que estar ACTIVO, no realiza suma de intentos*/
		if(!userModel.getStatus().equalsIgnoreCase(StatusUser.ACTIVO.toString())) {
			//Se incrementa contador, por status
			loginResDto = UserLoginMapper.userToDto(userModel, "El usuario no cuenta con estatus ACTIVO, permiso denegado");
			LOGGER.error("Error en: login, error: estatus no es ACTIVO");
			userModel = this.save(userModel);
			return loginResDto;
		}
		/**Validacion de negocio, suma contador de sesion llega, si llega a 3, cambiar estatus a BLOQUEADO*/
		counterLogin+= counterAux;
		if(counterLogin >= 3) {
			userModel.setStatus(StatusUser.BLOQUEADO.toString());
			loginResDto = UserLoginMapper.userToDto(userModel, "Su usuario se encuentra bloqueado, favor de pasar a su sucursal");
			LOGGER.warn("Actualiza estatus en: login, con buc: {}",userModel.getBuc());
		}
		//Se guarda aumento de contador a login
		loginResDto.setAttempts(counterLogin);
		userModel.setAttempts(String.valueOf(counterLogin));
		userModel = this.save(userModel);
		return loginResDto;
	}

	/**
	 * Este metodo permite hacer la busqueda de un usuario por buc
	 * @param buc Dato por el cual se hara la busqueda
	 * @return regresa un UserDto con la informacion asociada a la consulta.
	 */
	@Override
	public FindByBucResDto findByBuc(String buc) {
		UserModel userModel = null;
		FindByBucResDto findByBucResDto = null;
		userModel = this.findByDn(buc);
		try {
			findByBucResDto = FindByBucMapper.userToDto(userModel);
		}catch (IllegalArgumentException e) {
			LOGGER.error("Error en: findbyDn, al mapear UserModel a DTO, error: {}",e.getMessage());
			throw new GeneralException(GeneralCatalog.GRAL004, "Error al mapear User, con buc: "+buc);
		}
		return findByBucResDto;
	}

	/**
	 * Este metodo permite hacer el enrolamiento, actualizando o agregando datos nuevos, asociados a los datos del usuario enviado
	 * @param datos a actualizar del usuario
	 * @return regresa un UserDto con los datos actualizados
	 */
	@Override
	public EnrollResDto enroll(EnrollReqDto enrollReqDto) {
		UserModel userModel = null;
		EnrollResDto enrollResDto = null;
		userModel = this.findByDn(enrollReqDto.getBuc());
		/**Validacion de negocio, no hacer enrolamiento, si ya esta enrolado*/
		if(!userModel.getStatus().equalsIgnoreCase(StatusUser.ENROLAR.toString())) { 
			LOGGER.error("El usuario ya esta enrrolado, con buc: {}",userModel.getBuc());
			throw new GeneralException(GeneralCatalog.GRAL004, "El recurso ya ha sido enrrolado, con buc: "+userModel.getBuc());
		}
		/**Campos a actualizar*/
		/**Validacion de negocio, se cambia estatus a ACTIVO*/
		userModel.setCreatAt(new Date().toString());
		userModel.setLastConecction(new Date().toString());
		userModel.setStatus(StatusUser.ACTIVO.toString());
		userModel.setAttempts("0");
		userModel.setPassword(CoderUtil.getEncoder(enrollReqDto.getPassword()));
		if(enrollReqDto.getMail() != null) {
			userModel.setMail(enrollReqDto.getMail());
		}
		userModel = this.save(userModel);
		//Se hace mapeo para salida final
		enrollResDto = EnrollMapper.userToDto(userModel);
		LOGGER.info("Exito al enrrollar registro, con buc: {}",enrollResDto.getBuc());		
		return enrollResDto;
	}

	/**
	 * Este metodo permite hacer el cambio de contraseña de un usuario.
	 * @param datos asociados al usuario para reazaliar la operacion
	 * @return regresa un userDto con los datos de la operacion.
	 */
	@Override
	public ChangePasswordResDto changePassword(ChangePasswordReqDto changePassReqDto) {
		//Instancias
		UserModel userModel = null;
		ChangePasswordResDto changePassResDto = null;
		Calendar calendar = GregorianCalendar.getInstance();
		Queue<String> passwords = new LinkedList<>();
		
		/**Se busca usuario por buc*/
		userModel = this.findByDn(changePassReqDto.getBuc());
		
		/**Se recupera lista de contraseñas*/
		String passwordUserModel = CoderUtil.getDecoder(userModel.getPassword());	//ArrayBytes a String
		String passSeparated [] = passwordUserModel.split(",");	//Separan contraseñas por el caracter ","
		//Se pasan contraseñas a estructura de datos cola para procesar los datos
		for(String passAux : passSeparated) {
			passwords.add(passAux);
		}
		
		/**Validacion de negocio, no se puede cambiar password si el estatus del usuario no esta ACTIVO o BLOQUEADO*/
		if(!userModel.getStatus().equalsIgnoreCase(StatusUser.ACTIVO.toString()) && !userModel.getStatus().equalsIgnoreCase(StatusUser.BLOQUEADO.toString())) {
			LOGGER.error("El usuario no puede realizar esta operacion, con buc: {}",userModel.getBuc());
			throw new GeneralException(GeneralCatalog.GRAL004, "El usuario no puede realizar esta operación, no está ACTIVO");
		}
		/**Validacion de negocio, si es cambio de password voluntario es false*/
		if(!changePassReqDto.isLocked() && changePassReqDto.getOldPassword() != null) {
			/**Validacion de negocio, validar password actual sea el mismo*/
			if(!changePassReqDto.getOldPassword().equalsIgnoreCase(passwords.peek())) {
				LOGGER.error("Error al realizar operacion cambio de password, password actual no es el mismo ingresado.");
				throw new GeneralException(GeneralCatalog.GRAL004, "Error al validar contraseña actual y enviada, no son las mismas");
			}
		}
		 /**Validacion de negocio, cambio password por bloqueo, tiene que pasar 2 hrs*/
		if(userModel.getStatus().equalsIgnoreCase(StatusUser.BLOQUEADO.toString()) && changePassReqDto.isLocked() == true) {
			Calendar calendarAux = GregorianCalendar.getInstance();
			//Resta 2 hrs a la hora actual
			calendarAux.add(Calendar.HOUR_OF_DAY, -2);
			Date lastConnecction = new Date(userModel.getLastConecction());
			if(lastConnecction.after(calendarAux.getTime())) { //Valida, si lastConection es superior a la hora actual
				LOGGER.warn("No han pasado 2 hrs, no se puede realziar cambio de password");
				throw new GeneralException(GeneralCatalog.GRAL004, "Deben de trascurrir 2 horas desde el último intento de inicio de sesión, para realizar el cambio de contraseña");
			}
		}
		
		/**Validacion de negocio, validar ultimos 10 password no sean el mismo que el nuevo*/	
		LOGGER.info("Password a buscar: {}",changePassReqDto.getPassword());
		boolean existPass = passwords.stream().anyMatch(p -> p.equalsIgnoreCase(changePassReqDto.getPassword()));
		if(existPass) {
			LOGGER.error("Error: La contraseña ya se ha registrado anteriormente");
			throw new GeneralException(GeneralCatalog.GRAL004, "La contraseña ya se ha registrado anteriormente");
		}
		/**Se elimina ultimo password de la lista*/
		if(passwords.size() == 10) {
			passwords.poll();
		}
		/**Se agrega nuevo password*/
		passwords.add(changePassReqDto.getPassword());

		StringBuffer passwordsToSave = new StringBuffer();
		for(String pass : passwords) {
			passwordsToSave.append(pass).append(",");
		} 
		
		//Campos a actualziar
		userModel.setPassword(CoderUtil.getEncoder(passwordsToSave.toString()));
		userModel.setLastConecction(calendar.getTime().toString());
		/**Validacion de negocio, numero de intentos se regresa a 0, se cambia estatus usuario, cuando esta bloqueado a activo*/
		if(userModel.getStatus().equalsIgnoreCase(StatusUser.BLOQUEADO.toString())) {
			userModel.setStatus(StatusUser.ACTIVO.toString());
			userModel.setAttempts("0");
		}
		//Se envia peticion
		userModel = this.save(userModel);
		//Se hace mapeo para salida final
		changePassResDto = ChangePasswordMapper.userToDto(userModel);
		LOGGER.info("Exito al cambiarPassword registro, con buc: {}",changePassResDto);		
		return changePassResDto;
	}
	
	/**
	 * Este servicio permite crear la cadena para hacer la busqueda a los registro de LDAP
	 * @param employeeNumber Id del registro para realizar la busqueda 
	 * @return cadena de busqueda construida
	 */
    private Name buildDn(String employeeNumber) {
        return LdapNameBuilder.newInstance(BASE_DN).add("ou", "people").add("employeeNumber", employeeNumber).build();
    }
    
    /**
     * Este metodo permite crear y asociar los atribiutos de acuerdo a los registro de LDAP, con el modelo
     * @param userM Modelo creado a partir de los registros
     * @return regresa los atributos mapeados del modelo
     */
    private Attributes buildAttributes(UserModel userM) {
    	BasicAttribute ocattr = new BasicAttribute("objectclass");
        ocattr.add("top");
        ocattr.add("person");
        Attributes attrs = new BasicAttributes();
        attrs.put(ocattr);
        attrs.put("employeeNumber",userM.getBuc());
        attrs.put("cn",userM.getName());
        attrs.put("sn",userM.getSurnames());
        attrs.put("description",userM.getCreatAt());
        attrs.put("initials",userM.getLastConecction());
        attrs.put("mail",userM.getMail());
        attrs.put("st",userM.getAttempts());
        attrs.put("telephoneNumber",userM.getNumberPhone());
        attrs.put("title",userM.getStatus());
        attrs.put("userPassword",userM.getPassword());
        return attrs;
    }
    
    
    public String reset(String buc) {
    	String str = "Error no se pudo resetear user, con buc: "+buc;
    	try{
    		UserModel userModel = findByDn(buc);
    		if(!userModel.getStatus().equalsIgnoreCase("BAJA")) { 
    			userModel.setCreatAt("");
        		userModel.setLastConecction("");
        		userModel.setPassword(CoderUtil.getEncoder(buc));
            	userModel.setStatus(StatusUser.ENROLAR.toString());
            	userModel.setAttempts("0");
            	this.save(userModel);
            	str = "Reseteo de usuario con exito, buc: "+ buc; 	
    		}
    	}catch (Exception e) {
    		LOGGER.error("Error al resetear usaurio, con buc: {}",buc);
    		return str;
		}
    	
    	return str;
    }

}