package mx.santander.fiduciario.authcontrol.mapper;

import java.util.Date;

import mx.santander.fiduciario.authcontrol.dto.login.post.LoginResDto;
import mx.santander.fiduciario.authcontrol.model.UserModel;

public class UserLoginMapper {

	private UserLoginMapper() {}
	
	public static LoginResDto userToDto(UserModel userModel) {
		LoginResDto userLogin = null;
		userLogin = LoginResDto.builder()
						.buc(userModel.getBuc())
						.name(userModel.getName())
						.surnames(userModel.getSurnames())
						.lastConecction(!userModel.getLastConecction().equalsIgnoreCase("") ? new Date(userModel.getLastConecction()) : null)
						.attempts(Integer.parseInt(userModel.getAttempts()))
						.status(userModel.getStatus())
						.build();
		return userLogin;
	}
	
	public static LoginResDto userToDto(UserModel userModel, String message) {
		LoginResDto userLogin = null;
		userLogin = LoginResDto.builder()
						.buc(userModel.getBuc())
						.name(userModel.getName())
						.surnames(userModel.getSurnames())
						.lastConecction(!userModel.getLastConecction().equalsIgnoreCase("") ? new Date(userModel.getLastConecction()) : null)
						.attempts(Integer.parseInt(userModel.getAttempts()))
						.status(userModel.getStatus())
						.message(message)
						.build();
		return userLogin;
	}
	
	public static LoginResDto userToDtoNotNames(UserModel userModel, String message) {
		LoginResDto userLogin = null;
		userLogin = LoginResDto.builder()
						.buc(userModel.getBuc())
						.lastConecction(!userModel.getLastConecction().equalsIgnoreCase("") ? new Date(userModel.getLastConecction()) : null)
						.attempts(Integer.parseInt(userModel.getAttempts()))
						.status(userModel.getStatus())
						.message(message)
						.build();
		return userLogin;
	}
}
