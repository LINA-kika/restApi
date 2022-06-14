package ru.rubcon.restApi.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.models.User;

import javax.validation.constraints.Pattern;


@Setter
@Getter
public class PutAdminUserDto extends BasicClientUserDto {

    private String username;
    private String password;

    public PutAdminUserDto(String lastName, String firstName, String patronymic, String email, @Pattern(regexp = "(^$|[0-9]{11})") String phone, String username, String password) {
        super(lastName, firstName, patronymic, email, phone);
        this.username = username;
        this.password = password;
    }


    public static PutAdminUserDto convertToPutAdminUserDto(User user) {
        return new PutAdminUserDto(
                user.getLastName(),
                user.getFirstName(),
                user.getPatronymic(),
                user.getEmail(),
                user.getPhone(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public static User convertFromPutAdminUserDto(PutAdminUserDto putAdminUserDto) {
        User user = new User();
        user.setLastName(putAdminUserDto.getLastName());
        user.setFirstName(putAdminUserDto.getFirstName());
        user.setPatronymic(putAdminUserDto.getPatronymic());
        user.setEmail(putAdminUserDto.getEmail());
        user.setPhone(putAdminUserDto.getPhone());
        user.setUsername(putAdminUserDto.getUsername());
        user.setPassword(putAdminUserDto.getPassword());
        return user;
    }
}
