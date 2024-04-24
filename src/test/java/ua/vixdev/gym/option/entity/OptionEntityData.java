package ua.vixdev.gym.option.entity;

import ua.vixdev.gym.options.entity.OptionEntity;

import java.util.List;

public abstract class OptionEntityData {
    public static OptionEntity getSingleOption(){
        return new OptionEntity(true,"key","value",true);
    }
    public static OptionEntity getSingleOptionWithId(){
        OptionEntity option = new OptionEntity(true,"key","value",true);
        option.setId(1L);
        return option;
    }
    public static List<OptionEntity> getListOfOption(){
        return List.of(new OptionEntity(true,"key","value",true));
    }
}
