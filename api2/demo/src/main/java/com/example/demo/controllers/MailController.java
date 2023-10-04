package com.example.demo.controllers;

import com.example.demo.Entities.MailStructure;
import com.example.demo.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")

public class MailController {
    @Autowired
    private MailService ms;
    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure){

        ms.sendMail(mail,mailStructure);
        return "Successfully sent email";
    }
}
