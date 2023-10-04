package com.example.demo.controllers;

import com.example.demo.Converter.AccountConverter;
import com.example.demo.Entities.AccountEntity.AccountDto;
import com.example.demo.Entities.AccountEntity.AccountEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import com.example.demo.Exceptions.AccountException;
import com.example.demo.Exceptions.UserException;
import com.example.demo.Service.AccountService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountConverter accountConverter; // Injectez AccountConverter

    @Autowired
    public AccountController(AccountService accountService, AccountConverter accountConverter) {
        this.accountService = accountService;
        this.accountConverter = accountConverter;
    }
    @Autowired
    private UserService userService;

    @GetMapping("/byUser")
    public ResponseEntity<?> getAccountByUser(@RequestParam long userId) {
        try {
            UserEntity user = userService.findById(userId); // Obtenez l'utilisateur à partir de votre service d'utilisateur
            if (user == null) {
                throw new UserException("L'utilisateur avec l'ID " + userId + " n'a pas été trouvé.");
            }

            AccountEntity account = accountService.findByUser(user);
            if (account == null) {
                throw new AccountException("Aucun compte n'a été trouvé pour cet utilisateur.");
            }

            AccountDto accountDto = accountConverter.entityToDTO(account);

            return ResponseEntity.ok(accountDto);
        } catch (UserException ex) {
            // Gérez explicitement l'exception UserException ici
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (AccountException ex) {
            // Gérez explicitement l'exception AccountException ici
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            // Gérez d'autres exceptions ici, si nécessaire
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur interne du serveur s'est produite.");
        }
    }



    @GetMapping("/byStatut")
    public ResponseEntity<?> getAccountsByStatut(@RequestParam String statut) {
        try {
            List<AccountEntity> accounts = accountService.findAllByStatut(statut);
            if (accounts.isEmpty()) {
                throw new AccountException("Aucun compte n'a été trouvé pour le statut spécifié.");
            }
            return ResponseEntity.ok(accounts);
        } catch (AccountException ex) {
            // Gérez explicitement l'exception AccountException ici
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            // Gérez d'autres exceptions ici, si nécessaire
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur interne du serveur s'est produite.");
        }
    }


    @GetMapping("/countByStatut")
    public ResponseEntity<?> countAccountsByStatut(@RequestParam String statut) {
        Long count = accountService.countByStatut(statut);
        if (count == 0) {
            throw new AccountException("Aucun compte n'a été trouvé pour le statut spécifié.");
        }
        return ResponseEntity.ok(count);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        try {
            AccountEntity account = accountService.findById(id).orElse(null);

            if (account == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le compte avec l'ID spécifié n'a pas été trouvé.");
            }

            accountService.deleteById(id);
            return ResponseEntity.ok("Compte supprimé avec succès.");
        } catch (AccountException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression du compte.");
        }
    }



    @PutMapping("/{id}")
    public AccountDto updateAccount(@PathVariable Long id, @RequestBody AccountEntity updatedAccount) {
        // Mettez à jour l'entité AccountEntity et obtenez la version mise à jour
        AccountEntity updatedEntity = accountService.updateAccount(updatedAccount);

        // Convertissez l'entité mise à jour en DTO AccountDto en utilisant AccountConverter
        AccountDto updatedDto = accountConverter.entityToDTO(updatedEntity);

        return updatedDto; // Renvoyez le DTO mis à jour dans la réponse
    }
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountEntity newAccount) {
        // Assurez-vous que newAccount est correctement rempli avec les données nécessaires

        // Par exemple, vous pourriez vérifier si l'utilisateur associé existe
        UserEntity user = userService.findById(newAccount.getId());
        if (user == null) {
            throw new UserException("L'utilisateur associé n'a pas été trouvé.");
        }

        // Ensuite, vous pouvez créer le compte en utilisant le service d'accountService
        AccountEntity createdAccount = accountService.createAccount(newAccount);

        // Convertissez l'entité créée en DTO AccountDto en utilisant AccountConverter
        AccountDto createdDto = accountConverter.entityToDTO(createdAccount);

        return ResponseEntity.ok(createdDto);
    }

}








