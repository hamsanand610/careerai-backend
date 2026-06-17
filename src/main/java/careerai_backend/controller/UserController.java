package careerai_backend.controller;

import careerai_backend.dto.LoginRequest;
import careerai_backend.dto.RegisterRequest;
import careerai_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import careerai_backend.service.JwtService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/test")
    public String test() {
        return "User API Working Successfully 🚀";
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }

    @GetMapping("/profile")
    public String profile(@RequestHeader("Authorization") String token) {

    token = token.replace("Bearer ", "");

    if (!jwtService.validateToken(token)) {
        return "Invalid Token";
    }

    String email = jwtService.extractEmail(token);

    return "Welcome " + email;
}

}