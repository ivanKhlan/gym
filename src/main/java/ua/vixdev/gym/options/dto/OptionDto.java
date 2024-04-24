package ua.vixdev.gym.options.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.options.entity.OptionEntity;
import ua.vixdev.gym.options.mapper.ModelMapper;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto implements ModelMapper {
    @NotNull(message = "{option.key.notBlank}")
    @Size(max = 70,message = "{option.key.invalid}")
    private String key;
    private String value;
    private boolean visible;
    private boolean autoload;
    @Override
    public OptionEntity fromDto() {
        return new OptionEntity(
                autoload,
                key,
                value,
                visible);
    }
}
