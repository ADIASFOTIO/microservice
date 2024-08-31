package com.fotio.taskservice.configuration;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfiguration {

    @Value("${ms.rabbit.queue.name}")
    private String queueName;
    @Value("${ms.rabbit.routing.name}")
    private String routingName;
    @Value("${ms.rabbit.exchange.name}")
    private String exchangeName;


    @Bean
    public Queue queue()
    {
        return new Queue(queueName,true);
    }
    @Bean
    public DirectExchange directExchange()
    {
        return new DirectExchange(exchangeName);
    }
    @Bean
    Binding binding(Queue queue, DirectExchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with(routingName);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    public AmqpTemplate rabbitTemplate (ConnectionFactory connectionFactory)
    {
        final RabbitTemplate rabbitTemplate=
                new RabbitTemplate((org.springframework.amqp.rabbit.connection.ConnectionFactory) connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
