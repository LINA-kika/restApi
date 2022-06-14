package ru.rubcon.restApi.dto.call;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.models.CallStatus;
import ru.rubcon.restApi.models.MasterCall;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CreateCallDto {
    private String theme;
    private String reason;



    public static MasterCall convertFromCreateCallDto(CreateCallDto createCallDto) {
        MasterCall call = new MasterCall();
        call.setTheme(createCallDto.getTheme());
        call.setReason(createCallDto.getReason());
        call.setCallStatus(CallStatus.WAITING);
        call.setDepartureCallDate(new Timestamp(System.currentTimeMillis()));
        return call;
    }
}
