package com.example.Online_Market.repository.role;

import com.example.Online_Market.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role,Long> {
}
