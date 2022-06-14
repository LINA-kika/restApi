package ru.rubcon.restApi.services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.rubcon.restApi.dto.call.CreateCallDto;
import ru.rubcon.restApi.models.Construction;
import ru.rubcon.restApi.models.MasterCall;
import ru.rubcon.restApi.repos.ConstructionRepository;
import ru.rubcon.restApi.repos.MasterCallRepository;


import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class MasterCallService {
    private final MasterCallRepository masterCallRepository;
    private final ConstructionRepository constructionRepository;

    public MasterCallService(MasterCallRepository masterCallRepository, ConstructionRepository constructionRepository) {
        this.masterCallRepository = masterCallRepository;
        this.constructionRepository = constructionRepository;
    }

    public MasterCall addToConstructionGetById(Long constId, CreateCallDto createCallDto) {
        Optional<Construction> construction = constructionRepository.findById(constId);
        MasterCall call = CreateCallDto.convertFromCreateCallDto(createCallDto);
        call.setConstId(construction.get());

        MasterCall result = masterCallRepository.save(call);

        log.info("IN addToConstructionGetById - {} mastercall added to construction {}", result, constId);
        return result;
    }

    public void delete(Long id) {
        masterCallRepository.deleteById(id);
        log.info("IN delete - mastercall with id: {} successfully deleted");
    }

    public List<MasterCall> getAll() {
        List<MasterCall> result = masterCallRepository.findAll();
        log.info("IN getAll - {} mastercalls found", result.size());
        return result;
    }

    public Page<MasterCall> getCallsByConstId(Long constId, Pageable pageable) {
        Page<MasterCall> result = masterCallRepository.findByConstIdId(constId, pageable);
        if (result == null) {
            log.warn("IN getCallsByConstId - no mastercalls found for construction with id: {}", constId);
            return null;
        }

        log.info("IN getCallsByConstId - mastercall: {} found by construction with id: {}", result);
        return result;
    }

    public MasterCall getById(Long id) {
        MasterCall result = masterCallRepository.findById(id).get();
        if (result == null) {
            log.warn("IN getById - no mastercall found by id: {}", id);
            return null;
        }

        log.info("IN getById - mastercall: {} found by id: {}", result);
        return result;
    }

    public void update(MasterCall masterCall) {
        MasterCall result = masterCallRepository.save(masterCall);
        log.info("IN update - mastercall: {} successfully updated", result);
    }

    public Page<MasterCall> getCallsByName(String name, Long constId, Pageable pageable){
        Page<MasterCall> result = masterCallRepository.findByThemeStartingWithIgnoreCaseAndConstIdId(name,constId, pageable);
        if (result == null) {
            log.warn("IN getCallsByName - no mastercalls found with name: {}", name);
            return null;
        }

        log.info("IN getCallsByName - mastercall: {} found with theme: {}", result, name);
        return result;
    }

}
