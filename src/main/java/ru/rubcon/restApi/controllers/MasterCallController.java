package ru.rubcon.restApi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rubcon.restApi.dto.call.CallTableDto;
import ru.rubcon.restApi.dto.call.CreateCallDto;
import ru.rubcon.restApi.dto.call.ExtendedCallTableDto;
import ru.rubcon.restApi.dto.doc.GetAdminDocDto;
import ru.rubcon.restApi.models.Document;
import ru.rubcon.restApi.models.MasterCall;
import ru.rubcon.restApi.services.MasterCallService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "javainuseapi")
public class MasterCallController {
    private final MasterCallService masterCallService;
    @Value("${storage.location}")
    private String uploadPath;

    @Autowired
    public MasterCallController(MasterCallService masterCallService) {
        this.masterCallService = masterCallService;
    }

    @GetMapping(path = "admin/calls")
    public ResponseEntity<List<ExtendedCallTableDto>> getAll() {
        List<MasterCall> calls = masterCallService.getAll();
        List<ExtendedCallTableDto> callTableDtos = new ArrayList<>();
        calls.forEach(call -> {
            callTableDtos.add(ExtendedCallTableDto.convertToExtendedTableDto(call));
        });
        return new ResponseEntity<>(callTableDtos, HttpStatus.OK);
    }


    @GetMapping(path = "call/{callId}")
    public ResponseEntity<MasterCall> getById(@PathVariable Long callId) {
        MasterCall call = masterCallService.getById(callId);
        if (call == null) {
            return new ResponseEntity<MasterCall>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<MasterCall>(call, HttpStatus.OK);
    }

    @GetMapping(path = "call/construction/{constId}")
    public ResponseEntity<List<CallTableDto>> getCallsByConstId(@PathVariable Long constId,@RequestParam(defaultValue = "0") int page) {
        if (constId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.desc("departureCallDate")));
        Page<MasterCall> calls = masterCallService.getCallsByConstId(constId, pageable);
        List<CallTableDto> callTableDtos = new ArrayList<>();
        calls.forEach(
                call -> {
                    callTableDtos.add(CallTableDto.convertToCallTableDto(call));
                }
        );
        return new ResponseEntity<>(callTableDtos, HttpStatus.OK);
    }

    @DeleteMapping(path = "admin/call/{callId}")
    public ResponseEntity<MasterCall> delete(@PathVariable Long callId) {
        if (callId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        masterCallService.delete(callId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(path = "client/call/construction/{constId}")
    public ResponseEntity<MasterCall> addToConstructionGetById(@PathVariable Long constId, @RequestBody CreateCallDto createCallDto) {
        HttpHeaders headers = new HttpHeaders();

        if (constId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        masterCallService.addToConstructionGetById(constId, createCallDto);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }


    @PutMapping(path = "admin/call/{callId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MasterCall> patch(@PathVariable Long callId, @RequestPart("file") MultipartFile file, @RequestPart("entity") String call) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        if (call == null || callId == null) {
            return new ResponseEntity<MasterCall>(HttpStatus.BAD_REQUEST);
        }
        MasterCall currentCall = masterCallService.getById(callId);
        ObjectMapper objectMapper = new ObjectMapper();
        MasterCall putAdminCallDto = objectMapper.readValue(call, MasterCall.class);
        currentCall.setCallStatus(putAdminCallDto.getCallStatus() != null ? putAdminCallDto.getCallStatus() : currentCall.getCallStatus());
        currentCall.setMasterArrivingDate(putAdminCallDto.getMasterArrivingDate() != null ? putAdminCallDto.getMasterArrivingDate() : currentCall.getMasterArrivingDate());
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath + "/call");

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/call/" + resultFilename));
            currentCall.setReportPath("/file/call/"+resultFilename);
            masterCallService.update(currentCall);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping(path = "call/findByName/construction/{constId}")
    public ResponseEntity<List<CallTableDto>> sortDocsByTypeForConst(@RequestParam String callName, @PathVariable Long constId, @RequestParam(defaultValue = "0") int page) {
             Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.desc("departureCallDate")));
        Page<MasterCall> calls = masterCallService.getCallsByName(callName, constId, pageable);
        List<CallTableDto> callTableDtos = new ArrayList<>();
        calls.forEach(
                call -> {
                    callTableDtos.add(CallTableDto.convertToCallTableDto(call));
                }
        );
        return new ResponseEntity<>(callTableDtos, HttpStatus.OK);
    }
}
