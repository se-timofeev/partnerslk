package ru.planetnails.partnerslk.broker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableRabbit
public class RabbitConfig {

    @Value("${rabbit.host}")
    private String host;
    @Value("${rabbit.user}")
    private String user;
    @Value("${rabbit.password}")
    private String password;
    @Value("${rabbit.virtualHost}")
    private String virtualHost;
    @Value("${rabbit.port}")
    private Integer port;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory =
                new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(user);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue sendQueue() {
        return new Queue("send");
    }

    @Bean
    public Queue receiveQueue() {
        return new Queue("receive");
    }

    @Bean
    public DirectExchange exchanger() {
        return new DirectExchange("main", true, false);
    }

    @Bean
    public Binding sendBinding(Queue sendQueue, DirectExchange exchanger) {
        return BindingBuilder.bind(sendQueue).to(exchanger).with("1C");
    }

    @Bean
    public Binding receiveBinding(Queue receiveQueue, DirectExchange exchanger) {
        return BindingBuilder.bind(receiveQueue).to(exchanger).with("Java");
    }
}
