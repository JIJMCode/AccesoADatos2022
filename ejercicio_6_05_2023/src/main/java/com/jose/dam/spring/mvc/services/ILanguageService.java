package com.jose.dam.spring.mvc.services;

import java.util.List;

import com.jose.dam.spring.mvc.entity.Language;

public interface ILanguageService {

	public List<Language> findAll();
}
