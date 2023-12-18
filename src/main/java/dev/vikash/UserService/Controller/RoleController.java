package dev.vikash.UserService.Controller;

import dev.vikash.UserService.DTO.CreateRoleRequestDto;
import dev.vikash.UserService.Model.Role;
import dev.vikash.UserService.Service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
   private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @PostMapping
    public ResponseEntity<Role> createRole (@RequestBody CreateRoleRequestDto request){
       Role role=roleService.CreateRole(request.getName());
       return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
