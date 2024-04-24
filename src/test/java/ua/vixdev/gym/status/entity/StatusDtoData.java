package ua.vixdev.gym.status.entity;

import ua.vixdev.gym.status.dto.StatusDto;

public abstract class StatusDtoData {
    public static StatusDto getSingleStatusDto(){
        return new StatusDto("new",true);
    }
    public static StatusDto getUpdatedStatusDto(){
        return new StatusDto("new value",true);
    }
}
