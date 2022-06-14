package ru.rubcon.restApi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
@Setter
public class BasicClientUserDto {
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final String email;
    @Pattern(regexp = "(^$|[0-9]{11})")
    private final String phone;
}
