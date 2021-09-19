package mx.santander.fiduciario.authcontrol.service;

import mx.santander.fiduciario.authcontrol.dto.user.get.FindByBucResDto;
import mx.santander.fiduciario.authcontrol.dto.UserDto;
import mx.santander.fiduciario.authcontrol.dto.change.put.ChangePasswordReqDto;
import mx.santander.fiduciario.authcontrol.dto.change.put.ChangePasswordResDto;
import mx.santander.fiduciario.authcontrol.dto.enroll.post.EnrollReqDto;
import mx.santander.fiduciario.authcontrol.dto.enroll.post.EnrollResDto;
import mx.santander.fiduciario.authcontrol.dto.login.post.LoginReqDto;
import mx.santander.fiduciario.authcontrol.dto.login.post.LoginResDto;
import mx.santander.fiduciario.authcontrol.model.UserModel;

/**
 * @author Osvaldo Morales - (Arquetipo creado por Santander Tecnologia Mexico)
 * O
 */
public interface IUserLdapService  {
	
	UserModel findByDn(String dn);
	UserModel save(UserModel userModel);
	
	
	FindByBucResDto findByBuc(String buc);
	EnrollResDto enroll(EnrollReqDto enrrollReqDto);
	ChangePasswordResDto changePassword(ChangePasswordReqDto changePassReqDto);
	LoginResDto login(LoginReqDto loginReqDto);
	
	
	String reset(String buc);
    
//	@Cacheable("authcontrol")
		
	
	
//    @Cacheable("authcontrol")
//    @CacheEvict(value = "authcontrol", allEntries = true)
//    Iterable<UserLdap> findAll();
//
//    @CachePut("authcontrol")
//    <S extends UserLdap> S  save(S entity);
//
//    @CacheEvict("authcontrol")
//    void deleteById(Long id);

}
