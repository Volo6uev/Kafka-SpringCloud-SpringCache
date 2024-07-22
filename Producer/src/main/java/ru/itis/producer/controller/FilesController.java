package ru.itis.producer.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static ru.itis.producer.ProducerApplication.*;

@RequiredArgsConstructor
@RestController
public class FilesController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/files")
    public ResponseEntity<?> sendFile(@RequestParam("fileName") String fileName) {

        CompletableFuture<SendResult<String, String>> fileSendResult = kafkaTemplate.send(FILES_TOPIC, fileName);

        fileSendResult.whenComplete((result, ex) -> {
            if (ex != null) {
                throw new IllegalArgumentException(ex);
            } else {
                Objects.requireNonNull(result);
                System.out.println(result.getProducerRecord().value() + "  " + result.getRecordMetadata().toString());
            }
        });


        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
            CompletableFuture<SendResult<String, String>> imageSendResult = kafkaTemplate.send(IMAGES_TOPIC, fileName);
            imageSendResult.whenComplete((result, ex) -> {
                if (ex != null) {
                    throw new IllegalArgumentException(ex);
                } else {
                    Objects.requireNonNull(result);
                    System.out.println(result.getProducerRecord().value() + "  " + result.getRecordMetadata().toString());
                }
            });
        } else {
            CompletableFuture<SendResult<String, String>> documentsSendResult = kafkaTemplate.send(DOCUMENTS_TOPIC, fileName);
            documentsSendResult.whenComplete((result, ex) -> {
                if (ex != null) {
                    throw new IllegalArgumentException(ex);
                } else {
                    Objects.requireNonNull(result);
                    System.out.println(result.getProducerRecord().value() + "  " + result.getRecordMetadata().toString());
                }
            });
        }

        return ResponseEntity.ok().build();
    }

}
