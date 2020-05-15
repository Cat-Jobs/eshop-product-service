package com.goodgraces.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodgraces.eshop.data.QueueType;
import com.goodgraces.eshop.mapper.ProductMapper;
import com.goodgraces.eshop.model.Product;
import com.goodgraces.eshop.rabbitmq.RabbitMQSender;
import com.goodgraces.eshop.rabbitmq.RabbitQueue;
import com.goodgraces.eshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(Product product) {
		productMapper.add(product); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"add\", \"data_type\": \"product\", \"id\": " + product.getId() + "}");
	}

	public void update(Product product) {
		productMapper.update(product); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"update\", \"data_type\": \"product\", \"id\": " + product.getId() + "}");
	}

	public void delete(Long id) {
		productMapper.delete(id); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"delete\", \"data_type\": \"product\", \"id\": " + id + "}");
	}

	public Product findById(Long id) {
		return productMapper.findById(id);
	}

}
