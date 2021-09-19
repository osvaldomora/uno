package mx.santander.fiduciario.authcontrol.dto.user.get;

import java.io.Serializable;

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
public class FindByBucResDto implements Serializable{
	
	private String buc;	
	private String name;	
	private String surnames;	
	private String mail;	
	private String status;	
	private Integer attempts;	
	private static final long serialVersionUID = 1L;

}
