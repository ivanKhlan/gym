package ua.vixdev.gym.account.repositroy;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.account.entity.AccountEntity;


@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

}

