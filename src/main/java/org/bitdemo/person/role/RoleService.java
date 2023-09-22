package org.bitdemo.person.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role insertRole(String roleName){
        return roleRepository.save(new Role(roleName));
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
