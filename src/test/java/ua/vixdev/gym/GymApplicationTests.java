package ua.vixdev.gym;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.vixdev.gym.controller.AccountController;
import ua.vixdev.gym.model.Account;
import ua.vixdev.gym.repo.AccountRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class GymApplicationTests {


    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountRepo accountRepo;

    @Test
    public void testGetAllAccount() {
        // Arrange
        List<Account> mockAccountList = Arrays.asList(new Account(), new Account());
        when(accountRepo.findAll()).thenReturn(mockAccountList);

        // Act
        ResponseEntity<List<Account>> responseEntity = accountController.getAllAccount();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockAccountList, responseEntity.getBody());
    }

    @Test
    public void testGetAccountById() {
        // Arrange
        Long accountId = 1L;
        Account mockAccount = new Account();
        when(accountRepo.findById(accountId)).thenReturn(Optional.of(mockAccount));

        // Act
        ResponseEntity<Account> responseEntity = accountController.getAccountById(accountId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockAccount, responseEntity.getBody());
    }

    @Test
    public void testGetAccountByIdNotFound() {
        // Arrange
        Long accountId = 1L;
        when(accountRepo.findById(accountId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Account> responseEntity = accountController.getAccountById(accountId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testAddAccount() {
        // Arrange
        Account mockAccount = new Account();
        when(accountRepo.save(any(Account.class))).thenReturn(mockAccount);

        // Act
        ResponseEntity<Account> responseEntity = accountController.addAccount(mockAccount);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockAccount, responseEntity.getBody());
    }

    @Test
    public void testUpdateAccountById() {
        // Arrange
        Long accountId = 1L;
        Account existingAccount = new Account();
        existingAccount.setId(accountId);
        when(accountRepo.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepo.save(any(Account.class))).thenReturn(existingAccount);

        // Act
        ResponseEntity<Account> responseEntity = accountController.updateAccountById(accountId, new Account());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(existingAccount, responseEntity.getBody());
    }

    @Test
    public void testUpdateAccountByIdNotFound() {
        // Arrange
        Long accountId = 1L;
        when(accountRepo.findById(accountId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Account> responseEntity = accountController.updateAccountById(accountId, new Account());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteAccountById() {
        // Arrange
        Long accountId = 1L;

        // Act
        ResponseEntity<HttpStatus> responseEntity = accountController.deleteAccountById(accountId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(accountRepo, times(1)).deleteById(accountId);
    }

}
