package dev.vikash.UserService.Controller;

import dev.vikash.UserService.DTO.SetUserRolesRequestDto;
import dev.vikash.UserService.DTO.UserDto;
import dev.vikash.UserService.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetail(@PathVariable("id") Long UserId){
        UserDto userDto=userService.getUserDetail(UserId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable ("id") Long userId, @RequestBody SetUserRolesRequestDto request)
    {
        UserDto userDto=userService.setUserRole(userId,request.getRoleIds());
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
}
