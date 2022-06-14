package ru.rubcon.restApi.dto.call;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.models.CallStatus;
import ru.rubcon.restApi.models.MasterCall;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AdminCallDto {

    private Long id;
    private String theme;
    private String reason;
    private String reportPath;
    private Date departureCallDate;
    private Date masterArrivingDate;
    private CallStatus callStatus;
    private Date lastUpdateDate;


    public static AdminCallDto convertToAdminCallDto(MasterCall call){
        return new AdminCallDto(
                call.getId(),
                call.getTheme(),
                call.getReason(),
                call.getReportPath(),
                call.getDepartureCallDate(),
                call.getMasterArrivingDate(),
                call.getCallStatus(),
                call.getUpdated()
        );
    }

}
