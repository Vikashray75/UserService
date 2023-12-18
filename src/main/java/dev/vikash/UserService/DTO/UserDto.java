package dev.vikash.UserService.DTO;

import dev.vikash.UserService.Model.Role;
import dev.vikash.UserService.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles=new HashSet<>();
    public static UserDto from(User user)
    {
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
