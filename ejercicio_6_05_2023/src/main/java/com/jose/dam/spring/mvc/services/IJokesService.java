package com.jose.dam.spring.mvc.services;

import java.util.List;

import com.jose.dam.spring.mvc.dto.JokesDto;

public interface IJokesService {

	public List<JokesDto> findAll();
	
	public JokesDto getById(int id);

	//public List<JokesDto> findAllDto();
}
