package ua.vixdev.gym.status.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.status.entity.Status;
import ua.vixdev.gym.status.mapper.StatusMapper;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto implements StatusMapper {
    @NotNull(message = "Status value must not be null")
    @Size(max = 70, message = "Status value must be less than 70 characters")
    private String value;
    private boolean visible;

    @Override
    public Status fromDto() {
        return new Status(value,visible);
    }
}
