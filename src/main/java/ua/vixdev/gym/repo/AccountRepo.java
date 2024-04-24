package ua.vixdev.gym.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.model.Account;


@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

}

