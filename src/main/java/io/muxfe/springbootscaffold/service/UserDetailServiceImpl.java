package io.muxfe.springbootscaffold.service;

import io.muxfe.springbootscaffold.entity.User;
import io.muxfe.springbootscaffold.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository repository;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = repository.findByUsername(username);
    if (user == null) {
      return null;
    }
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (String role : user.getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
    }
    return new org.springframework.security.core.userdetails.User(
      user.getUsername(), user.getPassword(), grantedAuthorities);
  }
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
