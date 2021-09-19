package mx.santander.fiduciario.authcontrol.dto.change.put;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangePasswordReqDto implements Serializable{

	@NotNull
	private String buc;
	@NotNull
	private String password;
	private String oldPassword;
	@NotNull
	private boolean locked;
	private static final long serialVersionUID = 1L;
}
