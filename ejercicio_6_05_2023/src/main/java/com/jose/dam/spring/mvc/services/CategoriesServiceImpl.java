package com.jose.dam.spring.mvc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jose.dam.spring.mvc.dao.ICategoriesDAO;
import com.jose.dam.spring.mvc.entity.Categories;

@Service
public class CategoriesServiceImpl implements ICategoriesService {

	private ICategoriesDAO categoriesDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categories> findAll() {
		return (List<Categories>) categoriesDao.findAll();
	}

}
