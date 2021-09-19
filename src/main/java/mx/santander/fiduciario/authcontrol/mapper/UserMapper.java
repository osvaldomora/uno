package mx.santander.fiduciario.authcontrol.mapper;

import java.util.Calendar;
import java.util.Date;

import mx.santander.fiduciario.authcontrol.dto.UserDto;
import mx.santander.fiduciario.authcontrol.model.UserModel;

public class UserMapper {
	
	private UserMapper() {}
	
	public static UserModel userToModel(UserDto userDto) {
		UserModel userModel = null;
		userModel = UserModel.builder()
						.buc(userDto.getBuc())
						.name(userDto.getName())
						.surnames(userDto.getSurnames())
						.creatAt(userDto.getCreatAt().toString())
						.lastConecction(userDto.getLastConecction().toString())
						.mail(userDto.getMail())
						.attempts(String.valueOf(userDto.getAttempts()))
						.numberPhone(String.valueOf(userDto.getNumberPhone()))
						.status(userDto.getStatus())
						.password(userDto.getPassword())
						.build();
		return userModel;
	}
	
	public static UserDto userToDto(UserModel userModel) {
		UserDto userDto = null;
		userDto = UserDto.builder()
					.buc(userModel.getBuc() != null ? userModel.getBuc() : null)
					.name(userModel.getName() != null ? userModel.getName() : null)
					.surnames(userModel.getSurnames() != null ? userModel.getSurnames() : null)
					.creatAt(!userModel.getCreatAt().equalsIgnoreCase("") ? new Date(userModel.getCreatAt()) : null)
					.lastConecction(!userModel.getLastConecction().equalsIgnoreCase("") ? new Date(userModel.getLastConecction()) : null)
					.mail(userModel.getMail() != null ? userModel.getMail() : null)
					.attempts(userModel.getAttempts() != null ? Integer.parseInt(userModel.getAttempts()) : null)
					.numberPhone(userModel.getNumberPhone() != null ? Long.parseLong(userModel.getNumberPhone()) : null)
					.status(userModel.getStatus() != null ? userModel.getStatus() : null)
					.password(userModel.getPassword() != null ? userModel.getPassword() : null)
					.build();
		
		return userDto;
	}

}
