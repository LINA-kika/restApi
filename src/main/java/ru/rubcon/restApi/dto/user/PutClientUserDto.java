package ru.rubcon.restApi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.rubcon.restApi.models.User;

import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
public class PutClientUserDto {
    private String email;
    @Pattern(regexp = "(^$|[0-9]{11})")
    private String phone;

}
