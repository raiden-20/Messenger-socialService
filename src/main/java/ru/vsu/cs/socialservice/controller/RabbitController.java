package ru.vsu.cs.socialservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vsu.cs.socialservice.configuration.RabbitQueues;
import ru.vsu.cs.socialservice.dto.fields.IdDTO;
import ru.vsu.cs.socialservice.service.UserDataService;

@Controller
@RequiredArgsConstructor
public class RabbitController {

    private final UserDataService userDataService;

    @RabbitListener(queues = RabbitQueues.fromAuthQueue)
    public void createUser(@RequestBody IdDTO idDTO) {
        userDataService.createUser(idDTO);
    }
}
