package com.accenture.chat.repository;

import com.accenture.chat.exception.FileReadException;
import com.accenture.chat.util.FileReader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class AppChatRepository {

    private static final Map<String, String> repository = new ConcurrentHashMap<>();
    private static final Map<Integer, String> questionMap = new HashMap<>();

    public static final String DEFAULT_QUESTION = "Pregunta no encontrada";
    public static final String DEFAULT_ANSWER = "Como modelo de lenguaje limitado (MLL), no cuento con una " +
            "respuesta para tu pregunta en este momento";

    private static final String QUESTION_ANSWER_ONE;
    private static final String QUESTION_ANSWER_TWO;
    private static final String QUESTION_ANSWER_THREE;

    static {
        List<String> questionOneList;
        List<String> questionTwoList;
        List<String> questionThreeList;
        try {
            QUESTION_ANSWER_ONE = FileReader.readFile("src/main/resources/questions/question_answer_1.txt", StandardCharsets.UTF_8);
            QUESTION_ANSWER_TWO = FileReader.readFile("src/main/resources/questions/question_answer_2.txt", StandardCharsets.UTF_8);
            QUESTION_ANSWER_THREE = FileReader.readFile("src/main/resources/questions/question_answer_3.txt", StandardCharsets.UTF_8);
            questionOneList = mapStringToList(QUESTION_ANSWER_ONE);
            questionTwoList = mapStringToList(QUESTION_ANSWER_TWO);
            questionThreeList = mapStringToList(QUESTION_ANSWER_THREE);
        } catch (IOException e) {
            throw new FileReadException("error while reading file", e);
        }
        repository.put(questionOneList.get(0), questionOneList.get(1));
        repository.put(questionTwoList.get(0), questionTwoList.get(1));
        repository.put(questionThreeList.get(0), questionThreeList.get(1));

        questionMap.put(1, questionOneList.get(0));
        questionMap.put(2, questionTwoList.get(0));
        questionMap.put(3, questionThreeList.get(0));
    }

    public String findAnswerByQuestion(String question) {
        return repository.get(question);
    }

    public String findQuestionByKey(Integer key) {
        return questionMap.getOrDefault(key, DEFAULT_ANSWER);
    }

    public void saveQuestion(String key, String value, Integer questionCount) {
        questionMap.put(questionCount, key);
        repository.put(key, value);
    }

    private static List<String> mapStringToList(String s) {
        return Stream.of(s.split("/")).toList();
    }
}
