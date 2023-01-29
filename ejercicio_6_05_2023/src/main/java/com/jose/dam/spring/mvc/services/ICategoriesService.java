package com.jose.dam.spring.mvc.services;

import java.util.List;

import com.jose.dam.spring.mvc.entity.Categories;

public interface ICategoriesService {
	
	public List<Categories> findAll();
}
