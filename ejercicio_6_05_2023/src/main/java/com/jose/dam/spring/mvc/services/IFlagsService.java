package com.jose.dam.spring.mvc.services;

import java.util.List;

import com.jose.dam.spring.mvc.dto.FlagsDto;
import com.jose.dam.spring.mvc.entity.Flags;

public interface IFlagsService {
	public List<FlagsDto> findAll();
	
	public FlagsDto getById(int id);
}
