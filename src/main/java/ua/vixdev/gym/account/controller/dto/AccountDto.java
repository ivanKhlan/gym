package ua.vixdev.gym.account.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AccountDto {

    @NotNull
    private Long userId;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal balance;

    @NotNull
    private Long orderId;
}
