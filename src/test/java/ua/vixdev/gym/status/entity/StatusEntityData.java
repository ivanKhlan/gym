package ua.vixdev.gym.status.entity;

import java.util.List;

public abstract class StatusEntityData {
    public static StatusEntity getSingleStatus(){
        return new StatusEntity("new",true);
    }
    public static StatusEntity getSingleStatusWithId(){
        StatusEntity statusEntity = new StatusEntity("new",true);
        statusEntity.setId(1L);
        return statusEntity;
    }
public static List<StatusEntity> getListOfStatus(){
        return List.of(new StatusEntity("new",true));
}
}
