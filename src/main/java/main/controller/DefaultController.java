package main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DefaultController {
    @RequestMapping("/")
    public String nowDate() {
        return LocalDateTime.now().toString();
    }
}
