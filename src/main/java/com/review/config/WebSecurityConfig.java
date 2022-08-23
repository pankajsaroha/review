package com.review.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.review.config.CustomizeAuthenticationSuccessHandler;
import com.review.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private CustomizeAuthenticationSuccessHandler custAuthSuccessHandler;
	
	@Bean
	public UserDetailsService userDetails() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CustomizeAuthenticationSuccessHandler custHandler() {
		return new CustomizeAuthenticationSuccessHandler();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService userDetailsService = userDetails();
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/api/movies").permitAll()
			.antMatchers("/review/admin").hasAuthority("ADMIN").anyRequest()
			.authenticated().and().csrf().disable().formLogin().disable();//.formLogin().successHandler(custAuthSuccessHandler);
		http.httpBasic();
	}
	
	@Override
	public void configure(WebSecurity web) {
		web
			.ignoring()
			.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
}
