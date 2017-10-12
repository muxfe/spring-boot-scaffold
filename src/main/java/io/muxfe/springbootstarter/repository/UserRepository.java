package io.muxfe.springbootstarter.repository;

import io.muxfe.springbootstarter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);
}
