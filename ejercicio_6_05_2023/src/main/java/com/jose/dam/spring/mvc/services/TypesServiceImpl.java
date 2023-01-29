package com.jose.dam.spring.mvc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jose.dam.spring.mvc.dao.ITypesDAO;
import com.jose.dam.spring.mvc.entity.Types;

@Service
public class TypesServiceImpl implements ITypesService {

	private ITypesDAO typesDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Types> findAll() {
		return (List<Types>) typesDao.findAll();
	}

}
