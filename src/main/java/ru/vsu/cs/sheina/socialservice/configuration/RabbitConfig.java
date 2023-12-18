package ru.vsu.cs.sheina.socialservice.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue authServiceQueue() {
        return new Queue(RabbitQueues.fromAuthQueue);
    }

    @Bean
    public Queue fileServiceQueue() {
        return new Queue(RabbitQueues.toFileQueue);
    }
}
