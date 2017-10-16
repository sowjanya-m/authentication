package com.telstra.webauth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
	private SimpleAuthenticationSuccessHandler successHandler;

    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/resources/**", "/registration","/changepassword","/accountlocked", "/login").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                	.successHandler(successHandler)
                    .loginPage("/login")
                    .failureHandler(exceptionMappingAuthenticationFailureHandler())
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();        
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
    @Bean
    AuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler(){
        ExceptionMappingAuthenticationFailureHandler ex = new ExceptionMappingAuthenticationFailureHandler();
        Map<String, String> mappings = new HashMap<String, String>();
        mappings.put("org.springframework.security.authentication.CredentialsExpiredException", "/changepassword");
        mappings.put("org.springframework.security.authentication.LockedException", "/accountlocked");
        mappings.put("org.springframework.security.authentication.BadCredentialsException", "/login?error=true");
        ex.setExceptionMappings(mappings);
        return ex;
    }
    
}