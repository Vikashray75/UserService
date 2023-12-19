package dev.vikash.UserService.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity(name = "ECOM_USER")
public class User extends BaseModel{
    String email;
    String password;

    @ManyToMany
    private Set<Role> roles=new HashSet<>();
}
