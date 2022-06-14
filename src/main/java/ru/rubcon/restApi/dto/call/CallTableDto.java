package ru.rubcon.restApi.dto.call;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.models.CallStatus;
import ru.rubcon.restApi.models.MasterCall;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class CallTableDto {
    private Long id;

    private String theme;

    private Date departureCallDate;


    private CallStatus callStatus;

    public static CallTableDto convertToCallTableDto(MasterCall call) {
        return new CallTableDto(
                call.getId(),
                call.getTheme(),
                call.getDepartureCallDate(),
                call.getCallStatus()
        );
    }

}
