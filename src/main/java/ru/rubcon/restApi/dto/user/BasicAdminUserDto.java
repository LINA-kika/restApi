package ru.rubcon.restApi.dto.user;


import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.models.User;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class BasicAdminUserDto extends BasicClientUserDto {
    private Long id;
    private String username;

    public BasicAdminUserDto(String lastName, String firstName, String patronymic, String email, @Pattern(regexp = "(^$|[0-9]{11})") String phone, Long id, String username) {
        super(lastName, firstName, patronymic, email, phone);
        this.id = id;
        this.username = username;
    }

    public static BasicAdminUserDto convertToBasicAdminUserDto(User user) {
        return new BasicAdminUserDto(
                user.getLastName(),
                user.getFirstName(),
                user.getPatronymic(),
                user.getEmail(),
                user.getPhone(),
                user.getId(),
                user.getUsername()
        );

    }

}
