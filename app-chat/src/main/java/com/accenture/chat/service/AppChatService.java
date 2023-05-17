package com.accenture.chat.service;

import com.accenture.chat.dto.QuestionAnswerDTO;
import reactor.core.publisher.Mono;

public interface AppChatService {

    Mono<QuestionAnswerDTO> getAnswer(Integer key);

    void addQuestion(QuestionAnswerDTO questionAnswer);
}
