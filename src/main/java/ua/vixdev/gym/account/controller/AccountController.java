package ua.vixdev.gym.account.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.account.entity.AccountEntity;
import ua.vixdev.gym.account.repositroy.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<List<AccountEntity>> getAllAccount() {
        try {
            List<AccountEntity> accountEntityList = new ArrayList<>();
            accountRepository.findAll().forEach(accountEntityList::add);

            if (accountEntityList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(accountEntityList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountEntity> getAccountById(@PathVariable Long id) {

        Optional<AccountEntity> accountData = accountRepository.findById(id);

        if (accountData.isPresent()) {
            return new ResponseEntity<>(accountData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<AccountEntity> addAccount(@RequestBody AccountEntity accountEntity) {
        AccountEntity accountEntityObj = accountRepository.save(accountEntity);

        return new ResponseEntity<>(accountEntityObj, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<AccountEntity> updateAccountById(@PathVariable Long id, @RequestBody AccountEntity newAccountDataEntity) {
        Optional<AccountEntity> oldAccountData = accountRepository.findById(id);

        if (oldAccountData.isPresent()) {
            AccountEntity updatedAccountDataEntity = oldAccountData.get();

            updatedAccountDataEntity.setUser_id(newAccountDataEntity.getUser_id());

            AccountEntity accountEntityObj = accountRepository.save(updatedAccountDataEntity);

            return new ResponseEntity<>(accountEntityObj, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAccountById(@PathVariable Long id) {

        accountRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

