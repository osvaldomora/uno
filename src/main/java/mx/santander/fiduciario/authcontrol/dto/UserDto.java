package mx.santander.fiduciario.authcontrol.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String buc;	//employeeNumber
	private String name;	//cn
	private String surnames;	//sn
	private Date creatAt;// Fecha de alta /description
	private Date lastConecction;	//initials
	private String mail;	//mail
	private Integer attempts;	//Intentos logueo / st
	private Long numberPhone;	//telephoneNumber
	private String status;	//Estatus usuario /tittle
	private byte[] password;	//userPassword
}
