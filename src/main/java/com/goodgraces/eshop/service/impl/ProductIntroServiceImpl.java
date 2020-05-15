package com.goodgraces.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodgraces.eshop.data.QueueType;
import com.goodgraces.eshop.mapper.ProductIntroMapper;
import com.goodgraces.eshop.model.ProductIntro;
import com.goodgraces.eshop.rabbitmq.RabbitMQSender;
import com.goodgraces.eshop.rabbitmq.RabbitQueue;
import com.goodgraces.eshop.service.ProductIntroService;

@Service
public class ProductIntroServiceImpl implements ProductIntroService {

	@Autowired
	private ProductIntroMapper productIntroMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(ProductIntro productIntro) {
		productIntroMapper.add(productIntro); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"add\", \"data_type\": \"product_intro\", \"id\": " + productIntro.getId() + ", \"product_id\": " + productIntro.getProductId() + "}");
	}

	public void update(ProductIntro productIntro) {
		productIntroMapper.update(productIntro); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"update\", \"data_type\": \"product_intro\", \"id\": " + productIntro.getId() + ", \"product_id\": " + productIntro.getProductId() + "}");
	}

	public void delete(Long id) {
		ProductIntro productIntro = findById(id);
		productIntroMapper.delete(id); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"delete\", \"data_type\": \"product_intro\", \"id\": " + id + ", \"product_id\": " + productIntro.getProductId() + "}");
	}

	public ProductIntro findById(Long id) {
		return productIntroMapper.findById(id);
	}

}
