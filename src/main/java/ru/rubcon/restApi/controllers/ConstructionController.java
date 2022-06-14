package ru.rubcon.restApi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rubcon.restApi.dto.construction.BaseConstructionDto;
import ru.rubcon.restApi.models.Construction;
import ru.rubcon.restApi.services.ConstructionService;


import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@SecurityRequirement(name = "javainuseapi")
public class ConstructionController {
    private final ConstructionService constructionService;

    @Value("${storage.location}")
    private String uploadPath;

    @Autowired
    public ConstructionController(ConstructionService constructionService) {
        this.constructionService = constructionService;
    }

    @DeleteMapping(path = "admin/construction/{constId}")
    public ResponseEntity<Construction> delete(@Valid @PathVariable Long constId) {
        if (constId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        constructionService.delete(constId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/construction/user/{userId}")
    public List<Construction> getAll(@Valid @PathVariable Long userId) {
        return constructionService.getAllByUser(userId);
    }


    @PostMapping(path = "admin/construction/user/{userId}")
    public ResponseEntity<Construction> addToUserGetById(@Valid @PathVariable Long userId, @RequestPart("file") MultipartFile file, @Valid @RequestPart("entity") String baseConstructionDto) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        if (baseConstructionDto == null) {
            return new ResponseEntity<Construction>(HttpStatus.BAD_REQUEST);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        BaseConstructionDto baseConstruction = objectMapper.readValue(baseConstructionDto, BaseConstructionDto.class);
        Construction construction = BaseConstructionDto.convertFromBaseConstructionDto(baseConstruction);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath + "/construction");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/construction/" + resultFilename));
            construction.setImage("file/construction/" + resultFilename);
            constructionService.addToUserGetById(userId, construction);
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PutMapping(path = "admin/construction/{constId}")
    public ResponseEntity<Construction> patch(@Valid @PathVariable Long constId,  @RequestBody BaseConstructionDto baseConstructionDto) {
        if (baseConstructionDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Construction currentConst = constructionService.getById(constId);
        currentConst.setCity(baseConstructionDto.getCity() == null ? currentConst.getCity() : baseConstructionDto.getCity());
        currentConst.setStreet(baseConstructionDto.getStreet() == null ? currentConst.getStreet() : baseConstructionDto.getStreet());
        currentConst.setRegion(baseConstructionDto.getRegion() == null ? currentConst.getRegion() : baseConstructionDto.getRegion());
        currentConst.setBuilding(baseConstructionDto.getBuilding() == null ? currentConst.getBuilding() : baseConstructionDto.getBuilding());
        constructionService.update(currentConst);
        System.out.println(currentConst.getStreet());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
