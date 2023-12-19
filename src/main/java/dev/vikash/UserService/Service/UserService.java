package dev.vikash.UserService.Service;

import dev.vikash.UserService.DTO.UserDto;
import dev.vikash.UserService.Model.Role;
import dev.vikash.UserService.Model.User;
import dev.vikash.UserService.Repository.RoleRepository;
import dev.vikash.UserService.Repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
public UserDto getUserDetail(Long UserId)
{
    Optional<User> userOptional=userRepository.findById(UserId);
    if(userOptional.isEmpty())
    {
        return null;
    }
    return UserDto.from(userOptional.get());
}

public UserDto setUserRole(Long UserId, List<Long> roleIds)
{
    Optional<User> userOptional=userRepository.findById(UserId);
    Set<Role> roles=roleRepository.findAllByIdIn(roleIds);
    if(userOptional.isEmpty())
    {
        return null;
    }
    User user=userOptional.get();
   // user.setRoles(Set.copyOf(roles));
    user.setRoles(roles);
    User saveduser=userRepository.save(user);
    return UserDto.from(saveduser);

}
}
