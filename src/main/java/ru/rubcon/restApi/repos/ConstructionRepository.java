package ru.rubcon.restApi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rubcon.restApi.models.Construction;
import ru.rubcon.restApi.models.User;

import java.util.List;

public interface ConstructionRepository extends JpaRepository<Construction, Long> {
    List<Construction> getAllByOwner(User user);
}
