package mx.santander.fiduciario.authcontrol.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;


@Configuration
public class WebSecurityConfig{ //extends WebSecurityConfigurerAdapter {

	
	
	/*
	
	@Value("${ldap.urls}")
	private String ldapUrls;

	@Value("${ldap.base.dn}")
	private String ldapBaseDn;

	@Value("${ldap.username}")
	private String ldapSecurityPrincipal;

	@Value("${ldap.password}")
	private String ldapPrincipalPassword;

	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;

	@Value("${ldap.enabled}")
	private String ldapEnabled;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll();
		// .and()
		// .formLogin();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		if(Boolean.parseBoolean(ldapEnabled)){
		auth
		.ldapAuthentication()
			.contextSource()
			.url(ldapUrls + ldapBaseDn)
			.managerDn(ldapSecurityPrincipal)
			.managerPassword(ldapPrincipalPassword)
			.and()
		.userDnPatterns(ldapUserDnPattern);
		} else {
		//auth
		//.inMemoryAuthentication()
		//.withUser(“user”).password(“password”).roles(“USER”)
		//.and()
		//.withUser(“admin”).password(“admin”).roles(“ADMIN”);

		}
	}
	
	
	*/
	
	
	
	
	
	
	
	/*
	@Override
	  public void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth
	      .ldapAuthentication()
	      	//cn=Manager,dc=maxcrc,dc=com
	        .userDnPatterns("uid={0},ou=people")
	        .groupSearchBase("ou=people")
	        .contextSource()
	          .url("ldap://localhost:389/dc=maxcrc,dc=com");
	          //.managerPassword("secret");
	          //.and()
	        //.passwordCompare()
	          //.passwordEncoder(new BCryptPasswordEncoder())
	          //.passwordAttribute("userPassword");
	  }	
	
	*/
	
	
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // Put whatever your HTTP security requirements are here.
//        http
//            .authorizeRequests()
//            .anyRequest().permitAll();
////            fullyAuthenticated()
////            .and()
////            .formLogin().permitAll();
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception
//    {
//        auth.ldapAuthentication()
//            .contextSource(contextSource())
//            // Authenticate users by email
//            //.userSearchFilter("(&(objectClass=inetOrgPerson)(mail={0}))")
//            // Authenticate users by LDAP username
//            .userSearchFilter("(&(objectClass=inetOrgPerson)(uid={0}))")
//            .groupRoleAttribute("cn")
//            // Return all groups that contain this member
//            .groupSearchFilter("(&(objectClass=groupOfNames)(member={0}))");
//    }
//
//    @Bean
//    LdapTemplate ldapTemplate()
//    {
//        return new LdapTemplate(contextSource());
//    }

//    @Bean
//    public LdapContextSource contextSource() {
//        LdapContextSource ctx = new LdapContextSource();
////        ctx.setUrl("ldaps://ldap.jumpcloud.com:389");
//        ctx.setUrl("ldap.jumpcloud.com:636");
//
//        // Set the username/password for the LDAP binding user configured in JumpCloud
////        ctx.setUserDn("uid=dev,ou=Users,o=612f8c473c3cf95b0644f669,dc=jumpcloud,dc=com");
//        	ctx.setUserDn("uid=dev,ou=Users,o=612f8c473c3cf95b0644f669,dc=jumpcloud,dc=com");
//        ctx.setUserDn("dc=santander,dc=com");
//        ctx.setPassword("Ab123456#");
//        ctx.setPassword("secret");
//        
//        // Set the base search parameters for user/group queries
//        ctx.setBase("ou=Users,o=612f8c473c3cf95b0644f669,dc=jumpcloud,dc=com");
//        ctx.setBase("dc=santander,dc=com");
//
//        return ctx;
//    }
	
/*	
	@Configuration
	@PropertySource("classpath:application.properties")
//	@EnableLdapRepositories
	public class LdapConfig {

	    @Autowired
	    private Environment env;

	    @Bean
	    public LdapContextSource contextSource() {
	        LdapContextSource contextSource = new LdapContextSource();
	        contextSource.setUrl(env.getProperty("ldap.url"));
	        contextSource.setBase(env.getRequiredProperty("spring.ldap.embedded.base-dn"));
	        contextSource.setUserDn(env.getRequiredProperty("spring.ldap.embedded.credential.username"));
	        contextSource.setPassword(env.getRequiredProperty("spring.ldap.embedded.credential.password"));
	        
	        contextSource.afterPropertiesSet();
	        return contextSource;
	    }

	    @Bean
	    public LdapTemplate ldapTemplate() {
	        return new LdapTemplate(contextSource());
	    }
	}
*/
}
