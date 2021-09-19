package mx.santander.fiduciario.authcontrol.mapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.AttributesMapper;

import mx.santander.fiduciario.authcontrol.model.UserModel;

public class UserAttribute implements AttributesMapper<UserModel>{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAttribute.class);
	
	@Override
	public UserModel mapFromAttributes(Attributes attributes) throws NamingException {
		UserModel userModel = null;
		
		userModel = UserModel.builder()
						.buc(null != attributes.get("employeeNumber")  ? attributes.get("employeeNumber").get().toString() : null)
						.name(null != attributes.get("cn") ? attributes.get("cn").get().toString() : null)
						.surnames(null != attributes.get("sn") ? attributes.get("sn").get().toString() : null)
						.creatAt(null != attributes.get("description") ? attributes.get("description").get().toString() : null)
						.lastConecction(null != attributes.get("initials") ? attributes.get("initials").get().toString() : null)
						.mail(null != attributes.get("mail") ? attributes.get("mail").get().toString() : null)
						.attempts(null != attributes.get("st") ? attributes.get("st").get().toString() : null)
						.numberPhone(null != attributes.get("telephoneNumber") ?attributes.get("telephoneNumber").get().toString() : null)
						.status(null != attributes.get("title") ? attributes.get("title").get().toString() : null)
						.password((byte[])attributes.get("userPassword").get())
						.build();
		
		
		return userModel;
	}

}
