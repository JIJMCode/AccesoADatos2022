package com.jose.dam.spring.mvc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jose.dam.spring.mvc.dao.IJokesDAO;
import com.jose.dam.spring.mvc.dto.DtoProfile;
import com.jose.dam.spring.mvc.dto.JokesDto;
import com.jose.dam.spring.mvc.entity.Jokes;

@Service
public class JokesServiceImpl implements IJokesService {

	@Autowired
	private IJokesDAO jokesDao;

	@Override
	@Transactional(readOnly = true)
	public List<JokesDto> findAll() {
		List<Jokes> jokes = (List<Jokes>) jokesDao.findAll();
		List<JokesDto> jokesDto = new ArrayList<>();
		jokes.forEach(e-> {
			JokesDto jokeDto = DtoProfile.mapJokesToJokesDto(e);
			jokesDto.add(jokeDto);
		});
		return jokesDto;
	}

	@Override
	public JokesDto getById(int id) {
		Jokes joke = jokesDao.findById(id).orElse(null);
		JokesDto jokeDto = DtoProfile.mapJokesToJokesDto(joke);
		return jokeDto;
	}

}
