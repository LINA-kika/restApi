package ru.rubcon.restApi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rubcon.restApi.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
