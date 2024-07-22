package ru.itis.consumer.consumers;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Profile("courses-consumer")
@Component
public class EntityConsumer {
    @KafkaListener(topics = "courses", groupId = "coursesGroupId")
    public void listenCourses(@Payload String message) {
        System.out.println("Courses " + message);
    }
}
