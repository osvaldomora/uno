package mx.santander.fiduciario.authcontrol.mapper;

import java.util.Date;

import mx.santander.fiduciario.authcontrol.dto.change.put.ChangePasswordResDto;
import mx.santander.fiduciario.authcontrol.model.UserModel;

public class ChangePasswordMapper {

	private ChangePasswordMapper() {}
	
	public static ChangePasswordResDto userToDto(UserModel userModel) {
		ChangePasswordResDto changePassResDto= null;
		changePassResDto = ChangePasswordResDto.builder()
								.buc(userModel.getBuc())
								.dateOperation(!userModel.getLastConecction().equalsIgnoreCase("") ? new Date(userModel.getLastConecction()) : null)
								.build();
		return changePassResDto;
	}
	
}
