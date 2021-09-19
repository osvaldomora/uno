package mx.santander.fiduciario.authcontrol.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.santander.fiduciario.authcontrol.dto.UserDto;
import mx.santander.fiduciario.authcontrol.dto.change.put.ChangePasswordReqDto;
import mx.santander.fiduciario.authcontrol.dto.change.put.ChangePasswordResDto;
import mx.santander.fiduciario.authcontrol.dto.enroll.post.EnrollReqDto;
import mx.santander.fiduciario.authcontrol.dto.enroll.post.EnrollResDto;
import mx.santander.fiduciario.authcontrol.dto.login.post.LoginReqDto;
import mx.santander.fiduciario.authcontrol.dto.login.post.LoginResDto;
import mx.santander.fiduciario.authcontrol.dto.user.get.FindByBucResDto;
import mx.santander.fiduciario.authcontrol.service.IUserLdapService;

/**
 * @author Osvaldo Morales - (Arquetipo creado por Santander Tecnologia Mexico)
 * 
 *         Esta clase se encarga de exponer los endpoints de acceso basado
 *         principios REST Existen ciertas consultas, bajas, altas y
 *         actualizaciones a una coleccion de recursos de authControl
 */
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST}, allowedHeaders = "*")
@RequestMapping("/authcontrol/v1")
public class AuthControlController {

	/** La Constante LOGGER. Obtiene el Logger de la clase */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthControlController.class);

	@Autowired
	private IUserLdapService iUserLdap;

	/**
	 * Este servicio permite consultar datos asociados a un buc, permitiendo saber el estatus del usuario
	 * @param buc a buscar
	 * @return Datos asociados al BUC y objeto JSON obtenido
	 */
//	@ApiOperation(value = "obtenr usuario", notes = "Usuario AuthControl", response=ResponseEntity.class, httpMethod="GET", authorizations = {@Authorization(value="apiKey")})
	@GetMapping(value = "/users/{buc}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findByBuc(@PathVariable String buc) {
		LOGGER.info("Entra a controller GET, findByBuc: {}",buc); //Encode.forJava(user.toString())
		FindByBucResDto findByBucResDto = iUserLdap.findByBuc(buc);
		return new ResponseEntity<>(findByBucResDto, HttpStatus.OK);
	}

	/**
	 * Servicio que implementa el login ingresando usuario y contraseña, validando datos y cambiando estatus de no. de intentos
	 * @param user JSON de entrada con los datos de buc y password
	 * @return Codigo de la operacion, y JSON asociado.
	 */
//	@ApiOperation(value = "Alta de AuthControl", notes = "En el header Location devuelve el recurso que fue registrado", response=ResponseEntity.class, httpMethod="POST", authorizations = {@Authorization(value="apiKey")})				    
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto loginReqDto) {
		LOGGER.info("Entra a controller POST, login: {}",loginReqDto); //Encode.forJava(user.toString())
		LoginResDto loginResDto = iUserLdap.login(loginReqDto);
		return ResponseEntity.status(HttpStatus.OK).body(loginResDto);

	}

	/**
	 * Servicio que realiza en enrolamiento de un usuario nuevo, agrega nuevos datos asocidados al usuario y cambia el estatus en activo.
	 * @param user JSON de entrada con datos a agregar
	 * @return Codigo de operacion y cambio de status, JSON asociado.
	 */	
//	@ApiOperation(value = "Actualizacion de AuthControl", notes = "Actualizacion de AuthControl", response=ResponseEntity.class, httpMethod="PUT", authorizations = {@Authorization(value="apiKey")})					    
	@PutMapping(value = "/enroll", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> enroll(@Valid @RequestBody EnrollReqDto enrollReqDto) {		
		LOGGER.info("Entra a controller PUT, login: {}",enrollReqDto); //Encode.forJava(user.toString())
		EnrollResDto enrollResDto = iUserLdap.enroll(enrollReqDto);
		return ResponseEntity.status(HttpStatus.OK).body(enrollResDto);
	}

	/**
	 * Servicio que permite cambiar la contraseña de un usuario activo
	 * @param user JSON de entrada con datos ascoiados a la operacion
	 * @return Codigo de operacion y JSON asociado.
	 */
	@PutMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> changePaswword(@Valid @RequestBody ChangePasswordReqDto changePassReqDto) {
		LOGGER.info("Entra a controller PUT, change-password: {}",changePassReqDto); //Encode.forJava(user.toString())
		ChangePasswordResDto changePassResDto = iUserLdap.changePassword(changePassReqDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(changePassResDto);

	}
	
	@GetMapping("/reset/{buc}")
	public ResponseEntity<?> reset(@PathVariable(value = "buc") String buc){
		String res = iUserLdap.reset(buc);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

}