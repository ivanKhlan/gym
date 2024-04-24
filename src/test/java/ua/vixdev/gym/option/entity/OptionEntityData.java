package ua.vixdev.gym.option.entity;

import ua.vixdev.gym.options.entity.Options;

import java.util.List;

public abstract class OptionEntityData {
    public static Options getSingleOption(){
        return new Options(true,"key","value",true);
    }
    public static Options getSingleOptionWithId(){
        Options option = new Options(true,"key","value",true);
        option.setId(1L);
        return option;
    }
    public static List<Options> getListOfOption(){
        return List.of(new Options(true,"key","value",true));
    }
}
