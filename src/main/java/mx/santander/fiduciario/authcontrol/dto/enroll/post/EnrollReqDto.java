package mx.santander.fiduciario.authcontrol.dto.enroll.post;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
public class EnrollReqDto implements Serializable{
	
	@NotNull
	private String buc;
	@NotNull
	private String password;
	@Email
	private String mail;
	private static final long serialVersionUID = 1L;
}
