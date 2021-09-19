package mx.santander.fiduciario.authcontrol.dto.login.post;

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
public class LoginResDto implements Serializable {
	
	private String buc;
	private String name;
	private String surnames;
	private boolean session;
	private String status;
	private String message;
	private Date lastConecction;
	private Integer attempts;
	private static final long serialVersionUID = 1L;
}
