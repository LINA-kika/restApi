package ru.rubcon.restApi.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rubcon.restApi.dto.doc.GetAdminDocDto;
import ru.rubcon.restApi.dto.doc.GetClientDocDto;
import ru.rubcon.restApi.dto.doc.InsertAdminDocDto;
import ru.rubcon.restApi.models.DocType;
import ru.rubcon.restApi.models.Document;
import ru.rubcon.restApi.services.DocumentService;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "javainuseapi")
public class DocumentController {
    private final DocumentService documentService;
    @Value("${storage.location}")
    private String uploadPath;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(path = "admin/docs")
    public ResponseEntity<List<Document>> getAll() {
        List<Document> docs = documentService.getAll();
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }

    @GetMapping(path = "doc/{docId}")
    public ResponseEntity<GetClientDocDto> getById(@PathVariable Long docId) {
        if (docId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Document doc = documentService.getById(docId);
        GetClientDocDto getClientDocDto = GetClientDocDto.convertToClientDocDto(doc);

        if (doc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getClientDocDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "admin/doc/{docId}")
    public ResponseEntity<Document> delete(@PathVariable Long docId) {
        if (docId == null) {
            return new ResponseEntity<Document>(HttpStatus.BAD_REQUEST);
        }
        documentService.delete(docId);
        return new ResponseEntity<Document>(HttpStatus.NO_CONTENT);
    }


    @GetMapping(path = "doc/construction/{constId}")
    public ResponseEntity<List<GetAdminDocDto>> getDocsByConstId(@PathVariable Long constId,@RequestParam(defaultValue = "0") int page) {
        if (constId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.desc("uploadDate")));
        Page<Document> doc = documentService.getDocsByConstId(constId, pageable);
        List<GetAdminDocDto> docs = new ArrayList<>();
        doc.forEach(document -> docs.add(GetAdminDocDto.convertToDocDto(document)));
        if (docs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }

    @GetMapping(path = "doc/findByName/construction/{constId}")
    public ResponseEntity<List<GetAdminDocDto>> sortDocsByTypeForConst(@RequestParam String docName, @PathVariable Long constId,@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.desc("uploadDate")));
        Page<Document> doc = documentService.findByDocName(docName, constId, pageable);
        List<GetAdminDocDto> docs = new ArrayList<>();
        doc.forEach(document -> docs.add(GetAdminDocDto.convertToDocDto(document)));
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }

    @GetMapping(path = "doc/findByNameAndType/construction/{constId}")
    public ResponseEntity<List<GetAdminDocDto>> sortDocsByTypeForConst( @RequestParam DocType type, @RequestParam String docName, @PathVariable Long constId,@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.desc("uploadDate")));
        Page<Document> doc = documentService.findByDocNameAndType(type, docName, constId, pageable);
        List<GetAdminDocDto> docs = new ArrayList<>();
        doc.forEach(document -> docs.add(GetAdminDocDto.convertToDocDto(document)));
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }



  /*  @GetMapping(path = "doc/findByType/construction/{constId}")
    public ResponseEntity<List<GetAdminDocDto>> sortDocsByTypeForConst(@RequestParam DocType type, @PathVariable Long constId,@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.desc("uploadDate")));
        Page<Document> doc = documentService.findByTypeOrderByUploadDate(type, constId, pageable);
        List<GetAdminDocDto> docs = new ArrayList<>();
        doc.forEach(document -> docs.add(GetAdminDocDto.convertToDocDto(document)));
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }*/



    @PostMapping(path = "admin/doc/construction/{constId}")
    public ResponseEntity<GetClientDocDto> addToConstructionGetById(@PathVariable Long constId, @RequestPart("file") MultipartFile file, @RequestPart("entity") String adminDocDto) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        if (adminDocDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        InsertAdminDocDto docDto = objectMapper.readValue(adminDocDto, InsertAdminDocDto.class);
        Document saveDoc = InsertAdminDocDto.convertFromInsertDocDto(docDto);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath + "/doc");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "_" + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/doc/" + resultFilename));
            saveDoc.setDocPath("/file/doc/"+resultFilename);
            Document doc = documentService.addToConstructionGetById(constId, saveDoc);
            return new ResponseEntity<GetClientDocDto>(headers, HttpStatus.OK);
        }
        return new ResponseEntity<GetClientDocDto>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "admin/doc/{docId}")
    public ResponseEntity<Document> patch(@PathVariable Long docId, @RequestBody InsertAdminDocDto insertAdminDocDto) {
        if (insertAdminDocDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Document currentDoc = documentService.getById(docId);
        currentDoc.setType(insertAdminDocDto.getType()==null?currentDoc.getType():insertAdminDocDto.getType());
        currentDoc.setDocName(insertAdminDocDto.getDocName()==null?currentDoc.getDocName():insertAdminDocDto.getDocName());
        documentService.update(currentDoc);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}