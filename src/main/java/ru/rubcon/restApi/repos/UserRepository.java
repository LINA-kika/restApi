package ru.rubcon.restApi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rubcon.restApi.models.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> lastNameStartsWith(String lastname);
}
