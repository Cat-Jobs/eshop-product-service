package com.goodgraces.eshop.service;

import com.goodgraces.eshop.model.ProductProperty;

public interface ProductPropertyService {
	
	public void add(ProductProperty productProperty);
	
	public void update(ProductProperty productProperty);
	
	public void delete(Long id);
	
	public ProductProperty findById(Long id);
	
	public ProductProperty findByProductId(Long productId);
	
}
