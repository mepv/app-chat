package com.accenture.chat.controller;

import com.accenture.chat.dto.QuestionAnswerDTO;
import com.accenture.chat.service.AppChatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/questions")
public class AppChatController {

    private final AppChatService appChatService;

    public AppChatController(AppChatService appChatService) {
        this.appChatService = appChatService;
    }

    @GetMapping("/{key}")
    public Mono<QuestionAnswerDTO> retrieveAnswer(@PathVariable(name = "key") Integer key) {
        return appChatService.getAnswer(key);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addQuestion(@RequestBody QuestionAnswerDTO questionAnswer) {
        appChatService.addQuestion(questionAnswer);
    }
}
