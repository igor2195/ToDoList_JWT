package main.service;

import lombok.extern.slf4j.Slf4j;
import main.dto.AuthenticationRequestDto;

import main.dto.LoginDto;
import main.entity.User;
import main.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public LoginService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    public LoginDto login(AuthenticationRequestDto requestDto) {
        try {
            String userName = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, requestDto.getPassword()));
            User user = userService.findByUserName(userName);

            if (user == null) {
                throw new UsernameNotFoundException("User with userName: " + userName + " not found");
            }

            String token = jwtTokenProvider.createToken(userName, user.getRoles());

            LoginDto response = new LoginDto(userName, token);

            log.info("IN authenticate - user: {} successfully authenticated", userName);
            return response;


        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
