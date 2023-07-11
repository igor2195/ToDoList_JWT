package main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequestDto {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
