package ua.vixdev.gym.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.model.Account;
import ua.vixdev.gym.repo.AccountRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {




    private AccountRepo accountRepo;


    @GetMapping("getAllAccount")
    public ResponseEntity<List<Account>> getAllAccount() {
        try {
            List<Account> accountList = new ArrayList<>();
            accountRepo.findAll().forEach(accountList::add);

            if (accountList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(accountList, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAccountById/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {

        Optional<Account> accountData = accountRepo.findById(id);

        if (accountData.isPresent()) {
            return new ResponseEntity<>(accountData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addAccount")
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        Account accountObj = accountRepo.save(account);

        return new ResponseEntity<>(accountObj, HttpStatus.OK);
    }

    @PostMapping("/updateAccountById/{id}")
    public ResponseEntity<Account> updateAccountById(@PathVariable Long id, @RequestBody Account newAccountData) {
        Optional<Account> oldAccountData = accountRepo.findById(id);

        if (oldAccountData.isPresent()) {
            Account updatedAccountData = oldAccountData.get();

            updatedAccountData.setUser_id(newAccountData.getUser_id());

            Account accountObj = accountRepo.save(updatedAccountData);

            return new ResponseEntity<>(accountObj, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAccountById/{id}")
    public ResponseEntity<HttpStatus> deleteAccountById(@PathVariable Long id) {

        accountRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

