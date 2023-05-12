package com.accenture.chat.service;

import com.accenture.chat.dto.BillingDTO;
import com.accenture.chat.dto.QuestionAnswerDTO;
import com.accenture.chat.repository.AppChatRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import static com.accenture.chat.repository.AppChatRepository.DEFAULT_ANSWER;
import static com.accenture.chat.repository.AppChatRepository.DEFAULT_QUESTION;

@Service
public class AppChatServiceImpl implements AppChatService {

    private static final Logger log = LoggerFactory.getLogger(AppChatServiceImpl.class);
    private Integer count = 0;
    private Integer questionCount = 3;

    @Value("${billing.service.url}")
    private String billingServiceUrl;

    private final AppChatRepository appChatRepository;
    private final WebClient webClient;

    public AppChatServiceImpl(AppChatRepository appChatRepository, WebClient webClient) {
        this.appChatRepository = appChatRepository;
        this.webClient = webClient;
    }

    @Override
    public Mono<QuestionAnswerDTO> getAnswer(Integer key) {
        QuestionAnswerDTO answer = new QuestionAnswerDTO();
        String question = appChatRepository.findQuestionByKey(key);
        if (question.equals(DEFAULT_ANSWER)) {
            answer.setQuestion(DEFAULT_QUESTION);
            answer.setAnswer(DEFAULT_ANSWER);
            log.warn("Question not found - no charged");
        } else {
            answer.setQuestion(question);
            answer.setAnswer(appChatRepository.findAnswerByQuestion(question));
            processBillingService();
        }
        return Mono.just(answer);
    }

    @Override
    public void addQuestion(QuestionAnswerDTO questionAnswer) {
        questionCount++;
        log.info("Question added: {} - {}", questionAnswer.getQuestion(), questionAnswer.getAnswer());
        appChatRepository.saveQuestion(questionAnswer.getQuestion(), questionAnswer.getAnswer(), questionCount);
    }

    @CircuitBreaker(name = "billingService")
    public void sendChatUse(long id) {
        // TODO: falta configurar FallBackMethod
    }

    private void processBillingService() {
        BillingDTO billingDTO = new BillingDTO();
        count++;
        billingDTO.setCount(count);
        sendQuestionCount(billingDTO).subscribe();
    }

    private Mono<String> sendQuestionCount(BillingDTO billingDTO) {
        String url = UriComponentsBuilder.fromHttpUrl(billingServiceUrl)
                .path("/api/v1/billing")
                .buildAndExpand().toString();
        return webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(billingDTO))
                .retrieve()
                .bodyToMono(String.class)
                .log();
    }
}
