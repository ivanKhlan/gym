package ua.vixdev.gym.user.exceptions.data_validation;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2023-11-22
 */
public record Violation(String fieldName, String message) {

}
