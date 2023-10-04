package com.example.demo.Service;


import com.example.demo.Entities.AccountEntity.AccountEntity;
import com.example.demo.Entities.UserEntity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountEntity findByUser(UserEntity user);
    List<AccountEntity> findAllByStatut(String statut);
    Long countByStatut(String statut);

    // Ajoutez les méthodes suivantes
    void deleteById(Long id); // Supprimer un compte par ID
    AccountEntity updateAccount(AccountEntity account); // Mettre à jour un compte
    AccountEntity createAccount(AccountEntity account); // Créer un nouveau compte


    Optional<AccountEntity> findById(Long id);
}
