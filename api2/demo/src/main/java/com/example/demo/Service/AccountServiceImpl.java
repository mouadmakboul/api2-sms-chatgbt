package com.example.demo.Service;

import com.example.demo.Converter.AccountConverter;
import com.example.demo.Entities.AccountEntity.AccountDto;
import com.example.demo.Entities.AccountEntity.AccountEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import com.example.demo.Exceptions.AccountException;
import com.example.demo.Repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountConverter accountConverter;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo, AccountConverter accountConverter) {
        this.accountRepo = accountRepo;
        this.accountConverter = accountConverter;
    }

    @Override
    public AccountEntity findByUser(UserEntity user) {
        AccountEntity account = accountRepo.findByUser(user);
        if (account == null) {
            throw new AccountException("Le compte de l'utilisateur spécifié n'a pas été trouvé.");
        }
        return account;
    }


    @Override
    public List<AccountEntity> findAllByStatut(String statut) {
        return accountRepo.findAllByStatut(statut);
    }

    @Override
    public Long countByStatut(String statut) {
        return accountRepo.countByStatut(statut);
    }

    @Override
    public void deleteById(Long id) {
        accountRepo.deleteById(id);
    }

    @Override
    public AccountEntity updateAccount(AccountEntity account) {
        return accountRepo.save(account);
    }

    @Override
    public AccountEntity createAccount(AccountEntity account) {

        return accountRepo.save(account);
    }

    @Override
    public Optional<AccountEntity> findById(Long id) {
        return accountRepo.findById(id);
    }


    public AccountDto entityToDTO(AccountEntity accountEntity) {
        return accountConverter.entityToDTO(accountEntity);
    }
}
