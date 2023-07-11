package main.service.impl;

import lombok.extern.slf4j.Slf4j;

import main.dto.RegistrationRequestDto;
import main.entity.*;
import main.repository.RoleRepository;
import main.repository.UserRepository;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegistrationRequestDto requestDto) {
        User user = new User();
        user.setUserName(requestDto.getUserName());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(encodedPassword);
        user.setStatus(Status.ACTIVE);

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUserName(String username) {
        User result = userRepository.findByUserName(username);
        log.info("IN findByUserName - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.info("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);

        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
