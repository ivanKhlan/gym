package ua.vixdev.gym.status.entity;

import java.util.List;

public abstract class StatusEntityData {
    public static Status getSingleStatus(){
        return new Status("new",true);
    }
    public static Status getSingleStatusWithId(){
        Status status = new Status("new",true);
        status.setId(1L);
        return status;
    }
public static List<Status> getListOfStatus(){
        return List.of(new Status("new",true));
}
}
