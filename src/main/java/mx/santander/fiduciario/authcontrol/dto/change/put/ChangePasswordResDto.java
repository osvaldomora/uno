package mx.santander.fiduciario.authcontrol.dto.change.put;

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
public class ChangePasswordResDto implements Serializable{

	private String buc;
	private Date dateOperation;
	private static final long serialVersionUID = 1L;
}
