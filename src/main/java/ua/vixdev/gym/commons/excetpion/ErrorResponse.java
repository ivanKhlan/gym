package ua.vixdev.gym.commons.excetpion;

import java.util.Date;

public record ErrorResponse(String status, int statusCode, String message, Date date) {

}
