package com.cloud.strategy;

import java.net.URLEncoder;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.JmsException;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.cloud.util.PropertiesReader;


public class ObsDataMsgPublisher implements MessagePublisher {

	private static ObsDataMsgPublisher _instance = null;
	private static JmsTemplate jmsTemplate;

	private ObsDataMsgPublisher() {
	};

	public static synchronized ObsDataMsgPublisher getInstance() {
		if (_instance == null) {
			_instance = new ObsDataMsgPublisher();
			ActiveMQConnectionFactory mqFactory = new ActiveMQConnectionFactory();
			mqFactory.setBrokerURL(PropertiesReader.getProp("jms.url"));
			CachingConnectionFactory cachFactory = new CachingConnectionFactory(
					mqFactory);
			cachFactory.setSessionCacheSize(Integer.parseInt(PropertiesReader
					.getProp("jms.cachSessionNum")));
			jmsTemplate = new JmsTemplate(cachFactory);
			jmsTemplate.setPubSubDomain(false);// p2p方式
			jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);// 采用持久化方式
		}
		return _instance;
	}

	@Override
	public synchronized boolean sendByQuene(final String msg,
			final String queueName) {
		boolean ret = false;
		try {

			log.info(this.getClass().getSimpleName() + "--准备发送JMS消息，queueName："
					+ queueName);
			log.info(this.getClass().getSimpleName() + "--msg ：" + msg);
			jmsTemplate.setDefaultDestinationName(queueName);
			jmsTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session)
						throws JMSException {
					try {
						return session.createTextMessage(URLEncoder.encode(msg,
								"utf-8"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			});
			ret = true;
			log.info(this.getClass().getSimpleName() + "--JMS消息发送成功");
		} catch (JmsException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
