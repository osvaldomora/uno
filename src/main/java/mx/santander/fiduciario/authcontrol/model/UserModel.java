package mx.santander.fiduciario.authcontrol.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String buc;	//employeeNumber
	private String name;	//cn
	private String surnames;	//sn
	private String creatAt;// Fecha de alta /description
	private String lastConecction;	//initials
	private String mail;	//mail
	private String attempts;	//Intentos logueo / st
	private String numberPhone;	//telephoneNumber
	private String status;	//Estatus usuario /title
	private byte[] password;	//userPassword

}
