package ua.vixdev.gym;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.vixdev.gym.account.controller.AccountController;
import ua.vixdev.gym.account.entity.AccountEntity;
import ua.vixdev.gym.account.repositroy.AccountRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@SpringBootTest
class GymApplicationTestsEntity {


    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void testGetAllAccount() {
        // Arrange
        List<AccountEntity> mockAccountListEntity = Arrays.asList(new AccountEntity(), new AccountEntity());
        when(accountRepository.findAll()).thenReturn(mockAccountListEntity);

        // Act
        ResponseEntity<List<AccountEntity>> responseEntity = accountController.findAll();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockAccountListEntity, responseEntity.getBody());
    }

    @Test
    public void testGetAccountById() {
        // Arrange
        Long accountId = 1L;
        AccountEntity mockAccountEntity = new AccountEntity();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccountEntity));

        // Act
        ResponseEntity<AccountEntity> responseEntity = accountController.findAccountById(accountId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockAccountEntity, responseEntity.getBody());
    }

    @Test
    public void testGetAccountByIdNotFound() {
        // Arrange
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AccountEntity> responseEntity = accountController.findAccountById(accountId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testAddAccount() {
        // Arrange
        AccountEntity mockAccountEntity = new AccountEntity();
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(mockAccountEntity);

        // Act
        ResponseEntity<AccountEntity> responseEntity = accountController.createAccount(mockAccountEntity);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockAccountEntity, responseEntity.getBody());
    }

    @Test
    public void testUpdateAccountById() {
        // Arrange
        Long accountId = 1L;
        AccountEntity existingAccountEntity = new AccountEntity();
        existingAccountEntity.setId(accountId);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccountEntity));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(existingAccountEntity);

        // Act
        ResponseEntity<AccountEntity> responseEntity = accountController.updateAccountById(accountId, new AccountEntity());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(existingAccountEntity, responseEntity.getBody());
    }

    @Test
    public void testUpdateAccountByIdNotFound() {
        // Arrange
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AccountEntity> responseEntity = accountController.updateAccountById(accountId, new AccountEntity());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteAccountById() {
        // Arrange
        Long accountId = 1L;

        // Act
        ResponseEntity<HttpStatus> responseEntity = accountController.deleteById(accountId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(accountRepository, times(1)).deleteById(accountId);
    }

}
