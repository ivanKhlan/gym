package ua.vixdev.gym.options.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import ua.vixdev.gym.options.entity.Options;
import ua.vixdev.gym.options.mapper.ModelMapper;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOptionDto implements ModelMapper {
    @NotBlank(message = "{option.key.notBlank}")
    @Size(max = 70,message = "{option.key.invalid}")
    private String key;
    private String value;
    private boolean visible;
    private boolean autoload;
    @Override
    public Options fromDto() {
        return new Options(
                autoload,
                key,
                value,
                visible);
    }
}
