package ru.rubcon.restApi.dto.call;

import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.models.CallStatus;
import ru.rubcon.restApi.models.MasterCall;

import java.util.Date;

@Getter
@Setter
public class ExtendedCallTableDto extends CallTableDto {
    private Long userId;
    private String username;
    private Date masterArrivingDate;

    public ExtendedCallTableDto(Long id, String theme, Date departureCallDate, Date masterArrivingDate, CallStatus callStatus, Long userId, String username) {
        super(id, theme, departureCallDate, callStatus);
        this.userId = userId;
        this.masterArrivingDate = masterArrivingDate;
        this.username = username;
    }

    public static ExtendedCallTableDto convertToExtendedTableDto(MasterCall call) {
        return new ExtendedCallTableDto(
                call.getId(),
                call.getTheme(),
                call.getDepartureCallDate(),
                call.getMasterArrivingDate(),
                call.getCallStatus(),
                call.getConstId().getOwner().getId(),
                call.getConstId().getOwner().getUsername()
        );
    }
}
