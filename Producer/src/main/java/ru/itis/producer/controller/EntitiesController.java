package ru.itis.producer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.producer.clients.CoursesClient;
import ru.itis.producer.dto.CoursesDto;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static ru.itis.producer.ProducerApplication.*;

@RequiredArgsConstructor
@RestController
public class EntitiesController {

    @Autowired
    private CoursesClient coursesClient;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/entity")
    public ResponseEntity<?> sendEntity() {

        CoursesDto coursesDto = coursesClient.getCourses().stream().findFirst().orElseThrow(() -> new NullPointerException("Entity is empty"));

        CompletableFuture<SendResult<String, String>> entitySendResult = kafkaTemplate.send(COURSES_TOPIC, coursesDto.getTitle());

        entitySendResult.whenComplete((result, ex) -> {
            if (ex != null) {
                throw new IllegalArgumentException(ex);
            } else {
                Objects.requireNonNull(result);
                System.out.println(result.getProducerRecord().value() + "  " + result.getRecordMetadata().toString());
            }
        });

        return ResponseEntity.ok().build();
    }
}
