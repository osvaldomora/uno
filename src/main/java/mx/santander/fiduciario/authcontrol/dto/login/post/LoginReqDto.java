package mx.santander.fiduciario.authcontrol.dto.login.post;

import java.io.Serializable;

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
public class LoginReqDto implements Serializable{

	@NotNull
	private String buc;
	@NotNull
	private String password;
	private static final long serialVersionUID = 1L;
}
