package com.example.demo.controllers;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatgbtController {
    @GetMapping("/getChat/{prompt}")
    public CompletionResult getPrompt(@PathVariable String prompt){

        OpenAiService service=new OpenAiService("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");
          //  OpenAiService service = new OpenAiService("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");

            CompletionRequest completionRequest = CompletionRequest.builder()
                    .prompt(prompt)
                    .model("text-davinci-003")
                   .echo(true)
                    .build();
            return service.createCompletion(completionRequest);

        }


    }
