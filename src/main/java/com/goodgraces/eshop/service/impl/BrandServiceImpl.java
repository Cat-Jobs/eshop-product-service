package com.goodgraces.eshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodgraces.eshop.data.QueueType;
import com.goodgraces.eshop.mapper.BrandMapper;
import com.goodgraces.eshop.model.Brand;
import com.goodgraces.eshop.rabbitmq.RabbitMQSender;
import com.goodgraces.eshop.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	@Autowired
	private BrandMapper brandMapper;
	
	public void add(Brand brand) {
		brandMapper.add(brand); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"add\", \"data_type\": \"brand\", \"id\": " + brand.getId() + "}");
	}

	public void update(Brand brand) {
		brandMapper.update(brand); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"update\", \"data_type\": \"brand\", \"id\": " + brand.getId() + "}");
	}

	public void delete(Long id) {
		brandMapper.delete(id); 
		String queue = QueueType.getQueue(QueueType.REFRESH_OPTIONTYPE);
		rabbitMQSender.send(queue, "{\"event_type\": \"delete\", \"data_type\": \"brand\", \"id\": " +id + "}");
	}

	public Brand findById(Long id) {
		return brandMapper.findById(id);
	}
	
	public List<Brand> findByIds(String ids) {
		return brandMapper.findByIds(ids);
	}
}
