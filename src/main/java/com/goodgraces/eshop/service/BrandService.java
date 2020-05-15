package com.goodgraces.eshop.service;

import java.util.List;

import com.goodgraces.eshop.model.Brand;

public interface BrandService {
	
	public void add(Brand brand);
	
	public void update(Brand brand);
	
	public void delete(Long id);
	
	public Brand findById(Long id);
	
	public List<Brand> findByIds(String ids);
	
}
