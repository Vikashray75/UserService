package dev.vikash.UserService.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetUserRolesRequestDto {

    private List<Long> roleIds;
}
