package ua.vixdev.gym.account.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.account.controller.dto.AccountDto;
import ua.vixdev.gym.account.entity.AccountEntity;
import ua.vixdev.gym.account.service.AccountService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private static final Long EMPTY_ID = null;
    private final AccountService accountService;

    @GetMapping
    public List<AccountEntity> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public AccountEntity findAccountById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public AccountEntity createAccount(@RequestBody @Valid AccountDto accountDto) {
        return accountService.createAccount(mapToAccount(EMPTY_ID, accountDto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public AccountEntity updateAccount(@PathVariable Long id, @RequestBody @Valid AccountDto accountDto) {
        return accountService.updateAccount(mapToAccount(id, accountDto));
        }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        accountService.deleteById(id);
    }

    private AccountEntity mapToAccount(Long id, AccountDto accountDto) {
        return AccountEntity.builder()
                .id(id)
                .userId(accountDto.getUserId())
                .balance(accountDto.getBalance())
                .orderId(accountDto.getOrderId())
                .build();
    }

}

