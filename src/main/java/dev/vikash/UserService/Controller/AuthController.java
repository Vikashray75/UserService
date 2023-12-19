package dev.vikash.UserService.Controller;

import dev.vikash.UserService.DTO.LoginRequestDto;
import dev.vikash.UserService.DTO.SignUpRequestDto;
import dev.vikash.UserService.DTO.UserDto;
import dev.vikash.UserService.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request)
    {
        return authService.login(request.getEmail(),request.getPassword());
    }
    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request)
    {
        UserDto userDto=authService.signUp(request.getEmail(),request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }
}
