package ru.rubcon.restApi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

public @AllArgsConstructor
enum CallStatus{
    DONE("D"),
    WAITING("W"),
    CANCELED("C");

    @Getter
    final String type;
}
