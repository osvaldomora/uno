package mx.santander.fiduciario.authcontrol.mapper;

import mx.santander.fiduciario.authcontrol.dto.enroll.post.EnrollResDto;
import mx.santander.fiduciario.authcontrol.model.UserModel;

public class EnrollMapper {

	private EnrollMapper() {}
	
	public static EnrollResDto userToDto (UserModel userM) {
		EnrollResDto enrollResDto = null;
		
		enrollResDto = EnrollResDto.builder()
						.buc(userM.getBuc())
						.name(userM.getName())
						.surnames(userM.getSurnames())
						.status(userM.getStatus())
						.build();
		
		return enrollResDto;
	}
}
