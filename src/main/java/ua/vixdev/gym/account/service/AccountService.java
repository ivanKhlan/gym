package ua.vixdev.gym.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.account.entity.AccountEntity;
import ua.vixdev.gym.account.exception.AccountNotFoundException;
import ua.vixdev.gym.account.repositroy.AccountRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public List<AccountEntity> findAll() {
        return accountRepository.findAll();
    }
    public AccountEntity findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public AccountEntity createAccount(AccountEntity account) {
        return accountRepository.save(account);
    }

    public AccountEntity updateAccount(AccountEntity account) {
        validateAccountExists(account.getId());
        return accountRepository.save(account);
    }

    public void deleteById(Long id) {
        validateAccountExists(id);
        accountRepository.deleteById(id);
    }

    private void validateAccountExists(Long id) {
        if (accountRepository.findById(id).isEmpty()) {
            throw new AccountNotFoundException(id);
        }
    }
}
