package ru.rubcon.restApi.services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rubcon.restApi.dto.user.InsertAdminUserDto;
import ru.rubcon.restApi.models.Role;
import ru.rubcon.restApi.models.User;
import ru.rubcon.restApi.models.UserStatus;
import ru.rubcon.restApi.repos.RoleRepository;
import ru.rubcon.restApi.repos.UserRepository;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    public void add(User user){
        User result = userRepository.save(user);
        log.info("IN add - user: {} successfully added", result);
    }


    public List<User> getAll(){
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    public User getById(Long id){
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN getById - user: {} found by id: {}", result);
        return result;
    }

    /*public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }*/

    public List<User> findByLastname(String lastname) {
        List<User> result = userRepository.lastNameStartsWith(lastname);
        log.info("IN findByLastname - user: {} found by username: {}", result, lastname);
        return result;
    }



    public User register(InsertAdminUserDto insertAdminUserDto) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        User user = new User();
        user.setUsername(insertAdminUserDto.getUsername());
        user.setFirstName(insertAdminUserDto.getFirstName());
        user.setLastName(insertAdminUserDto.getLastName());
        user.setPhone(insertAdminUserDto.getPhone());
        user.setPatronymic(insertAdminUserDto.getPatronymic());
        user.setEmail(insertAdminUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(insertAdminUserDto.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(UserStatus.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }
}
