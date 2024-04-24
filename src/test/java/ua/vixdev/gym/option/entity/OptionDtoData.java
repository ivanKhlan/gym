package ua.vixdev.gym.option.entity;

import ua.vixdev.gym.options.dto.OptionDto;

public  abstract class OptionDtoData {
    public static OptionDto getSingleOptionDto(){
        return new OptionDto("key","value",true,true);
    }
    public static OptionDto getUpdatedOptionDto(){
        return new OptionDto("key","new value",true,true);
    }
}
