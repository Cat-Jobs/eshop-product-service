package com.goodgraces.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodgraces.eshop.data.QueueType;
import com.goodgraces.eshop.mapper.ProductPropertyMapper;
import com.goodgraces.eshop.model.ProductProperty;
import com.goodgraces.eshop.rabbitmq.RabbitMQSender;
import com.goodgraces.eshop.rabbitmq.RabbitQueue;
import com.goodgraces.eshop.service.ProductPropertyService;

@Service
public class ProductPropertyServiceImpl implements ProductPropertyService {

	@Autowired
	private ProductPropertyMapper productPropertyMapper;
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	public void add(ProductProperty productProperty) {
		productPropertyMapper.add(productProperty); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"add\", \"data_type\": \"product_property\", \"id\": " + productProperty.getId() + ", \"product_id\": " + productProperty.getProductId() + "}");
	}

	public void update(ProductProperty productProperty) {
		productPropertyMapper.update(productProperty); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"update\", \"data_type\": \"product_property\", \"id\": " + productProperty.getId() + ", \"product_id\": " + productProperty.getProductId() + "}");
	}

	public void delete(Long id) {
		ProductProperty productProperty = findById(id);
		productPropertyMapper.delete(id); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"delete\", \"data_type\": \"product_property\", \"id\": " + id + ", \"product_id\": " + productProperty.getProductId() + "}");
	}

	public ProductProperty findById(Long id) {
		return productPropertyMapper.findById(id);
	}
	
	public ProductProperty findByProductId(Long productId) {
		return productPropertyMapper.findByProductId(productId);
	}

}
