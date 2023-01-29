package com.jose.dam.spring.mvc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jose.dam.spring.mvc.dao.IFlagsDAO;
import com.jose.dam.spring.mvc.dto.DtoProfile;
import com.jose.dam.spring.mvc.dto.FlagsDto;
import com.jose.dam.spring.mvc.entity.Flags;

@Service
public class FlagsServiceImpl implements IFlagsService{

	@Autowired
	private IFlagsDAO flagsDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<FlagsDto> findAll() {
		List<Flags> flags = (List<Flags>) flagsDao.findAll();
		List<FlagsDto> flagsDto = new ArrayList<>();
		flags.forEach(e-> {
			FlagsDto flagDto = DtoProfile.mapFlagsToFlagsDto(e); 
			flagsDto.add(flagDto);
		});
		return flagsDto;
	}

	public FlagsDto getById(int id) {
		Flags flag = flagsDao.findById(id).orElse(null);
		FlagsDto flagDto = DtoProfile.mapFlagsToFlagsDto(flag); 
		return flagDto;
	}
}
