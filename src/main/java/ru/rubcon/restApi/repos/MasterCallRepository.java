package ru.rubcon.restApi.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.rubcon.restApi.models.Document;
import ru.rubcon.restApi.models.MasterCall;

import java.util.List;

public interface MasterCallRepository extends JpaRepository<MasterCall, Long> {
    Page<MasterCall> findByConstIdId(Long id, Pageable pageable);
    Page<MasterCall> findByThemeStartingWithIgnoreCaseAndConstIdId(String theme, Long id, Pageable pageable);

}
