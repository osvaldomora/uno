package mx.santander.fiduciario.authcontrol.mapper;

import mx.santander.fiduciario.authcontrol.dto.user.get.FindByBucResDto;
import mx.santander.fiduciario.authcontrol.model.UserModel;

public class FindByBucMapper {

	private FindByBucMapper() {}
	
	public static FindByBucResDto userToDto(UserModel userM) {
		FindByBucResDto findByBuc = null;
		
		findByBuc = FindByBucResDto.builder()
						.buc(userM.getBuc())
						.name(userM.getName())
						.surnames(userM.getSurnames())
						.mail(userM.getMail())
						.status(userM.getStatus())
						.attempts(Integer.parseInt(userM.getAttempts()))
						.build();
		
		return findByBuc;
	}
}
