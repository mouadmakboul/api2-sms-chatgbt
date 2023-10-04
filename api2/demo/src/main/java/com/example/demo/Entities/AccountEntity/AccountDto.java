package com.example.demo.Entities.AccountEntity;


import com.example.demo.Entities.UserEntity.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter



public class AccountDto {
    private long id;
    private String statut;
    private String account;
    //private UserDto user; // Utilisez un DTO pour l'utilisateur associé, par exemple, UserDto
}

