package ru.rubcon.restApi.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rubcon.restApi.dto.user.*;
import ru.rubcon.restApi.models.User;
import ru.rubcon.restApi.services.UserService;


import java.util.ArrayList;
import java.util.List;


@RestController
@SecurityRequirement(name = "javainuseapi")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "client/user/{userId}")
    public ResponseEntity<GetClientUserDto> getById(@PathVariable Long userId) {
       User convert = userService.getById(userId);
       if(convert==null){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
        GetClientUserDto user = GetClientUserDto.convertToClientUserDto(convert);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "admin/user/{userId}")
    public ResponseEntity<FullAdminUserDto> getFullUserById(@PathVariable Long userId) {
        FullAdminUserDto user = FullAdminUserDto.convertToFullAdminUserDto(userService.getById(userId));
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("admin/users")
    public ResponseEntity<List<BasicAdminUserDto>> getAll() {
        List<User> users = userService.getAll();
        List<BasicAdminUserDto> result = new ArrayList<>();
        for (User u : users) {
            result.add(BasicAdminUserDto.convertToBasicAdminUserDto(u));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

   @PostMapping("/admin/user")
    public ResponseEntity<User> add(@RequestBody InsertAdminUserDto insertAdminUserDto) {
        HttpHeaders headers = new HttpHeaders();
        userService.register(insertAdminUserDto);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(path = "admin/user/findByLastname")
    public ResponseEntity<List<BasicAdminUserDto>> findByLastname(@RequestParam String lastname) {
        HttpHeaders headers = new HttpHeaders();
        List<User> users = userService.findByLastname(lastname);
        List<BasicAdminUserDto> result = new ArrayList<>();
        for (User u : users) {
            result.add(BasicAdminUserDto.convertToBasicAdminUserDto(u));
        }
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @PutMapping(path = "client/user/{userId}")
    public ResponseEntity<GetClientUserDto> patch(@PathVariable Long userId, @RequestBody PutClientUserDto user) {
        User currentUser = userService.getById(userId);
        currentUser.setPhone(user.getPhone() != null ? user.getPhone() : currentUser.getPhone());
        currentUser.setEmail(user.getEmail() != null ? user.getEmail() : currentUser.getEmail());
        userService.add(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "admin/user/{userId}")
    public ResponseEntity<PutAdminUserDto> patch(@PathVariable Long userId, @RequestBody PutAdminUserDto user) {
        User currentUser = userService.getById(userId);
        currentUser.setPhone(user.getPhone() != null ? user.getPhone() : currentUser.getPhone());
        currentUser.setEmail(user.getEmail() != null ? user.getEmail() : currentUser.getEmail());
        currentUser.setLastName(user.getLastName() != null ? user.getLastName() : currentUser.getLastName());
        currentUser.setFirstName(user.getFirstName() != null ? user.getFirstName() : currentUser.getFirstName());
        currentUser.setPatronymic(user.getPatronymic() != null ? user.getPatronymic() : currentUser.getPatronymic());
        currentUser.setUsername(user.getUsername() != null ? user.getUsername() : currentUser.getUsername());
        currentUser.setPassword(user.getPassword() != null ? user.getPassword() : currentUser.getPassword());
        userService.add(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
