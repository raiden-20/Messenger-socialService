package ru.vsu.cs.sheina.socialservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.sheina.socialservice.configuration.RabbitQueues;
import ru.vsu.cs.sheina.socialservice.dto.fields.IdDTO;
import ru.vsu.cs.sheina.socialservice.dto.rabbitmq.FileDTO;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${storage.host}")
    private String storageHost;

    @Value("${storage.bucket}")
    private String bucket;


    public String sendRequestToFileService(FileDTO fileDTO) {
//        rabbitTemplate.convertSendAndReceive(RabbitQueues.toFileQueue, avatar);
        return storageHost + "/" + bucket + "/" + fileDTO.getFile().getOriginalFilename();
    }
}
