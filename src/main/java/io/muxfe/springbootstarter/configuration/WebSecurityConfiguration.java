package io.muxfe.springbootstarter.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final UserDetailsService userDetailsService;

  @Autowired
  public WebSecurityConfiguration(
    BCryptPasswordEncoder bCryptPasswordEncoder,
    UserDetailsService userDetailsService) {
    
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .authorizeRequests()
        .mvcMatchers("/api/users/**").hasRole("ADMIN")
        .mvcMatchers(HttpMethod.GET, "/api/**").permitAll()
        .mvcMatchers("/api/**").authenticated()
        .anyRequest().permitAll().and()
      .formLogin().and()
      .logout();
  }
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(userDetailsService)
      .passwordEncoder(bCryptPasswordEncoder);
  }
}
