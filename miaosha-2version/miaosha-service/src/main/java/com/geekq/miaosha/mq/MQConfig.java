package com.geekq.miaosha.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {

	/**
	 * /usr/sbin/rabbitmq-plugins enable rabbitmq_management
	 * mq页面
	 */
	public static final String MIAOSHA_QUEUE = "miaosha.queue";
	public static final String CHECK_MIAOSHA_QUEUE = "checkmiaosha.queue";

	public static final String EXCHANGE_TOPIC = "exchange_topic";
	public static final String MIAOSHA_MESSAGE = "miaosha_mess";

	public static final String MIAOSHATEST = "miaoshatest";

	public static final String QUEUE = "queue";
	public static final String TOPIC_QUEUE1 = "topic.queue1";//主题对列
	public static final String TOPIC_QUEUE2 = "topic.queue2";//主题对列
	public static final String HEADER_QUEUE = "header.queue";//
	public static final String DELAY_QUEUE_1 = "delay_queue_1";
	public static final String TOPIC_EXCHANGE = "topicExchage";//主题交换机
	public static final String FANOUT_EXCHANGE = "fanoutExchage";//广播交换机
	public static final String HEADERS_EXCHANGE = "headersExchage";
	public static final String DELAYED_EXCHANGE = "delayed_exchange";
	public static final String CHECK_MIAOSHA_EXCHANGE = "CHECK_MIAOSHA_EXCHANGE";



	/**
	 * Direct模式(默认) 交换机Exchange
	 * */
	@Bean
	public Queue queue() {
		return new Queue(QUEUE, true);
	}
    /*
    * 延时对列*/
	@Bean
	public Queue delayQueue(){
		Queue queue=new Queue(DELAY_QUEUE_1,true);
		return queue;
	}
    @Bean
	public Queue checkMiaoShaQueue(){
		Queue queue=new Queue(CHECK_MIAOSHA_QUEUE,true);
		return queue;
	}
	

	@Bean
	public Queue topicQueue1() {
		return new Queue(TOPIC_QUEUE1, true);
	}
	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE2, true);
	}
	/**
	 * Topic模式 交换机Exchange
	 * */
	@Bean
	public TopicExchange topicExchage(){
		return new TopicExchange(TOPIC_EXCHANGE);
	}
	@Bean
	public CustomExchange delayExchange(){
		Map<String,Object> map=new HashMap<>();
		map.put("x-delayed-type","direct");
		return new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",true,false,map);
	}
    @Bean
	public TopicExchange CHECK_MIAOSHA_EXCHANGE(){
		return new TopicExchange(CHECK_MIAOSHA_EXCHANGE);
	}
	@Bean
	public Binding topicBinding3(){
		return BindingBuilder.bind(checkMiaoShaQueue()).to(CHECK_MIAOSHA_EXCHANGE()).with("checkmiaosha");
	}

	@Bean
	public Binding topicBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(topicExchage()).with("topic.key1");
	}
	@Bean
	public Binding topicBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(topicExchage()).with("topic.#");
	}
	/**
	 * Fanout模式(广播) 交换机Exchange
	 * */
	@Bean
	public FanoutExchange fanoutExchage(){
		return new FanoutExchange(FANOUT_EXCHANGE);
	}
	@Bean
	public Binding FanoutBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(fanoutExchage());
	}
	@Bean
	public Binding FanoutBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(fanoutExchage());
	}
	/**
	 * Header模式 交换机Exchange
	 * */
	@Bean
	public HeadersExchange headersExchage(){
		return new HeadersExchange(HEADERS_EXCHANGE);
	}
	@Bean
	public Queue headerQueue1() {
		return new Queue(HEADER_QUEUE, true);
	}
	@Bean
	public Binding headerBinding() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("header1", "value1");
		map.put("header2", "value2");
		return BindingBuilder.bind(headerQueue1()).to(headersExchage()).whereAll(map).match();
	}

	@Bean
	public Binding delayBinding(){
		return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DELAY_QUEUE_1).noargs();
	}
	
	
}
