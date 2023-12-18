package dev.vikash.UserService.Service;

import dev.vikash.UserService.Model.Role;
import dev.vikash.UserService.Repository.RoleRepository;

public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role CreateRole(String name)
    {
        Role role=new Role();
        role.setRole(name);
        return roleRepository.save(role);
    }
}
