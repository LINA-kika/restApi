package ru.rubcon.restApi.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rubcon.restApi.models.Construction;
import ru.rubcon.restApi.models.User;
import ru.rubcon.restApi.repos.ConstructionRepository;
import ru.rubcon.restApi.repos.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ConstructionService {
    private final ConstructionRepository constructionRepository;
    private final UserRepository userRepository;

    public ConstructionService(ConstructionRepository constructionRepository, UserRepository userRepository) {
        this.constructionRepository = constructionRepository;
        this.userRepository = userRepository;
    }

    public Construction addToUserGetById(Long userId, Construction construction) {
        Optional<User> user = userRepository.findById(userId);
        construction.setOwner(user.get());
        Construction result = constructionRepository.save(construction);
        log.info("IN addToUserGetById - {} construction added to user {}", result, userId);
        return result;
    }

    public List<Construction> getAll() {
        List<Construction> result = constructionRepository.findAll();
        log.info("IN getAll - {} constructions found", result.size());
        return result;
    }

    public List<Construction> getAllByUser(Long userId) {
        User user = userRepository.getById(userId);

        List<Construction> result = constructionRepository.getAllByOwner(user);
        log.info("IN getAll - {} constructions found", result.size());
        return result;
    }

    public void delete(Long id) {
        constructionRepository.deleteById(id);
        log.info("IN delete - construction with id: {} successfully deleted");
    }

    public Construction getById(Long id) {
        Construction result = constructionRepository.findById(id).get();
        if (result == null) {
            log.warn("IN getById - no construction found by id: {}", id);
            return null;
        }

        log.info("IN getById - construction: {} found by id: {}", result);
        return result;
    }

    public void update(Construction construction) {
        Construction result = constructionRepository.save(construction);
        log.info("IN update - construction: {} successfully updated", result);
    }
}
