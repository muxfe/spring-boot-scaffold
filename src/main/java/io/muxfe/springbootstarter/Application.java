package io.muxfe.springbootstarter;

import io.muxfe.springbootstarter.entity.User;
import io.muxfe.springbootstarter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class Application {
  
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public Application(BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Bean
  public CommandLineRunner initilize(UserRepository repository) {
    return strings -> {
      if (repository.count() == 0) {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        repository.save(new User("user", bCryptPasswordEncoder.encode("pass"), roles, null));
        roles.clear();
        roles.add("ADMIN");
        repository.save(new User("admin", bCryptPasswordEncoder.encode("pass"), roles, null));
        roles.clear();
        roles.add("ACTUATOR");
        repository.save(new User("actuator", bCryptPasswordEncoder.encode("pass"), roles, null));
      }
    };
  }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
