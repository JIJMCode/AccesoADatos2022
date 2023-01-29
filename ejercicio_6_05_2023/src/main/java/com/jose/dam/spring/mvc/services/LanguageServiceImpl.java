package com.jose.dam.spring.mvc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jose.dam.spring.mvc.dao.ILanguageDAO;
import com.jose.dam.spring.mvc.entity.Language;

@Service
public class LanguageServiceImpl implements ILanguageService{

	private ILanguageDAO languageDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Language> findAll() {
		return (List<Language>) languageDao.findAll();
	}

}
