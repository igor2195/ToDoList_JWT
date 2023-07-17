package main.controller;

import lombok.RequiredArgsConstructor;
import main.dto.AuthenticationRequestDto;
import main.dto.LoginDto;
import main.dto.RegistrationRequestDto;
import main.service.LoginService;
import main.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final LoginService authenticationService;

    /**
     * @param requestDto - данные для авторизации
     * @return - возвращает токен
     */
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        LoginDto loginDto = authenticationService.login(requestDto);
        return ResponseEntity.ok(loginDto);
    }

    /**
     * @param requestDto данные для создания нового пользоватлея
     * @return Данные по зарегестрированному пользователю
     */
    @PostMapping(value = "/registration")
    public ResponseEntity createUser(@RequestBody RegistrationRequestDto requestDto) {
        if (!requestDto.getPassword().equals(requestDto.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пароли не совпали");
        }
        if (userService.findByUserName(requestDto.getUserName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Пользователь с логином: %s уже существует", requestDto.getUserName()));
        }
        userService.register(requestDto);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
