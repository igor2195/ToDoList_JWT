package main.service;

import main.dto.RegistrationRequestDto;
import main.entity.User;

import java.util.List;

public interface UserService {

    User register(RegistrationRequestDto requestDto);

    List<User> getAll();

    User findByUserName(String username);

    User findById(Long id);

    void delete(Long id);
}
