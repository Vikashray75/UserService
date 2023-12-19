package dev.vikash.UserService.Mapper;

import dev.vikash.UserService.DTO.UserDto;
import dev.vikash.UserService.Model.User;

public class UserEntityDTOMapper {
    public static UserDto getUserDtoFromUserEntity(User user)
    {
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
