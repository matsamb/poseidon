package com.auth2.oseidclient.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.auth2.oseidclient.entity.OseidUserDetails;
import com.auth2.oseidclient.service.user.OseidUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration /* extends WebSecurityConfigurerAdapter */ {

	@Autowired
	private DataSource dataSource;

	@Bean
	public OseidUserDetailsService oseidUserDetailsService() {
		return new OseidUserDetailsService();
	}

	/*
	 * @Override public void configure(HttpSecurity httpSecurity) throws Exception {
	 * 
	 * httpSecurity.csrf().disable() .authorizeRequests()
	 * .antMatchers("/admin").hasAnyRole("ADMIN")
	 * .antMatchers("/user").hasAnyRole("ADMIN", "USER")
	 * .antMatchers("/h2-console/**").permitAll() .anyRequest().authenticated()
	 * .and().sessionManagement() .and() .formLogin() .defaultSuccessUrl("/home")
	 * .and() .oauth2Login() .and() .httpBasic() .and()
	 * .exceptionHandling().accessDeniedPage("/forbidden") .and() .logout() ;
	 * 
	 * }
	 */

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf().disable()
			.authorizeRequests()
			.antMatchers("/admin").hasAnyRole("ADMIN")
			.antMatchers("/user").hasAnyRole("ADMIN", "USER")
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement()
			.and()
			.formLogin()
			.defaultSuccessUrl("/home")
			.and()
			.oauth2Login()
			.and()
			.httpBasic()
			.and()
			.exceptionHandling().accessDeniedPage("/forbidden")
			.and()
			.logout()
			;

		return httpSecurity.build();

	}

	/*
	 * @Override //@Bean public void configure(AuthenticationManagerBuilder auth)
	 * throws Exception {
	 * 
	 * auth.userDetailsService(oseidUserDetailsService()).passwordEncoder(
	 * passwordEncoder());
	 */
	/*
	 * auth .ldapAuthentication() .userDnPatterns("uid={0},ou=people")
	 * .groupSearchBase("ou=groups") .contextSource()
	 * .url("ldap://localhost:8081/dc=springframework,dc=org") .and()
	 * .passwordCompare() .passwordEncoder(passwordEncoder())
	 * .passwordAttribute("userPassword");
	 * 
	 * /* auth.jdbcAuthentication().dataSource(dataSource)
	 * .passwordEncoder(passwordEncoder())
	 * .usersByUsernameQuery("select email, password, true from users where email = ?"
	 * )
	 * .authoritiesByUsernameQuery("select fk_username, authority from authorities where fk_username = ? "
	 * ) ;
	 */
//	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(oseidUserDetailsService())
				.passwordEncoder(passwordEncoder())
				.and()
				.build()
				;

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		String currentEncoder = "Bcrypt";

		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(currentEncoder, new BCryptPasswordEncoder());

		return encoders.get(currentEncoder);

	}

}
