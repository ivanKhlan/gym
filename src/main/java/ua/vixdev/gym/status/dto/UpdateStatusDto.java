package ua.vixdev.gym.status.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import ua.vixdev.gym.status.entity.Status;
import ua.vixdev.gym.status.mapper.StatusMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusDto implements StatusMapper {
    @NotBlank(message = "Status value must not be blank")
    @Size(max = 70, message = "Status value must be less than 70 characters")
    private String value;
    private boolean visible;

    @Override
    public Status fromDto() {
        return new Status(value,visible);
    }
}
