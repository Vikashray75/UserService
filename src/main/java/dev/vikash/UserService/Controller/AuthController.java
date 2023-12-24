package dev.vikash.UserService.Controller;

import dev.vikash.UserService.DTO.LoginRequestDto;
import dev.vikash.UserService.DTO.SignUpRequestDto;
import dev.vikash.UserService.DTO.UserDto;
import dev.vikash.UserService.DTO.ValidateTokenRequestDto;
import dev.vikash.UserService.Model.Session;
import dev.vikash.UserService.Model.SessionStatus;
import dev.vikash.UserService.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/session")
    public ResponseEntity<List<Session>> getAllSession()
    {
        return authService.getAllSession();


    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> logout(@PathVariable("id") Long UserId,@RequestHeader("token") String token)
    {
        return authService.logout(token,UserId);
    }
    @PostMapping("/Validate")
    public ResponseEntity<SessionStatus> validateToken(ValidateTokenRequestDto request)
    {
        SessionStatus sessionStatus=authService.validate(request.getToken(),request.getUserId());
        return new ResponseEntity<>(sessionStatus,HttpStatus.OK);
    }
}
