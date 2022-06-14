package ru.rubcon.restApi.dto.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.rubcon.restApi.models.User;

import java.util.HashSet;
import java.util.Set;

@Getter@Setter
@NoArgsConstructor
public class InsertAdminUserDto {
    private String username;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String email;
    private String password;
    private String phone;

    public static User convertFromInsertAdminUserDto(InsertAdminUserDto insertAdminUserDto){
        User user = new User();
        user.setUsername(insertAdminUserDto.getUsername());
        user.setFirstName(insertAdminUserDto.getFirstName());
        user.setLastName(insertAdminUserDto.getLastName());
        user.setPhone(insertAdminUserDto.getPhone());
        user.setPatronymic(insertAdminUserDto.getPatronymic());
        user.setEmail(insertAdminUserDto.getEmail());
        user.setPassword(user.getPassword());
        return user;
    }
}
