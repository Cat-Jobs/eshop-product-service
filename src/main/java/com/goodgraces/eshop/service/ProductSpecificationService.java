package com.goodgraces.eshop.service;

import com.goodgraces.eshop.model.ProductSpecification;

public interface ProductSpecificationService {
	
	public void add(ProductSpecification productSpecification);
	
	public void update(ProductSpecification productSpecification);
	
	public void delete(Long id);
	
	public ProductSpecification findById(Long id);
	
	public ProductSpecification findByProductId(Long productId);
}
