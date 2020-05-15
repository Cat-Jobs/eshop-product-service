package com.goodgraces.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodgraces.eshop.data.QueueType;
import com.goodgraces.eshop.mapper.CategoryMapper;
import com.goodgraces.eshop.model.Category;
import com.goodgraces.eshop.rabbitmq.RabbitMQSender;
import com.goodgraces.eshop.rabbitmq.RabbitQueue;
import com.goodgraces.eshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	public void add(Category category) {
		categoryMapper.add(category); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"add\", \"data_type\": \"category\", \"id\": " + category.getId() + "}");

	}

	public void update(Category category) {
		categoryMapper.update(category); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"update\", \"data_type\": \"category\", \"id\": " + category.getId() + "}");

	}

	public void delete(Long id) {
		categoryMapper.delete(id); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"delete\", \"data_type\": \"category\", \"id\": " + id + "}");

	}

	public Category findById(Long id) {
		return categoryMapper.findById(id);
	}

}
