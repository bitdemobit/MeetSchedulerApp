package org.bitdemo.person.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/get-roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Role> getRole(){
        return roleService.getRoles();
    }

    @PostMapping(path = "/insert-role", produces = MediaType.APPLICATION_JSON_VALUE)
    public Role insertRole(@Param("roleName") String roleName){
        return roleService.insertRole(roleName);
    }
}
