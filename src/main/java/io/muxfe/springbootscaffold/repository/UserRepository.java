package io.muxfe.springbootscaffold.repository;

import io.muxfe.springbootscaffold.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);
}
