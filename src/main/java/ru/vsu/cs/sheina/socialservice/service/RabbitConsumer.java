package ru.vsu.cs.sheina.socialservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vsu.cs.sheina.socialservice.configuration.RabbitQueues;
import ru.vsu.cs.sheina.socialservice.dto.fields.IdDTO;

@Service
@RequiredArgsConstructor
public class RabbitConsumer {

    private final UserDataService userDataService;

//    @RabbitListener(queues = RabbitQueues.fromAuthQueue)
//    public void createUser(@RequestBody IdDTO idDTO) {
//        userDataService.createUser(idDTO);
//    }
}
