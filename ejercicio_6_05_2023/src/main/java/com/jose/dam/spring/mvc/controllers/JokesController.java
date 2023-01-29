package com.jose.dam.spring.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jose.dam.spring.mvc.dto.JokesDto;
import com.jose.dam.spring.mvc.entity.Jokes;
import com.jose.dam.spring.mvc.services.JokesServiceImpl;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class JokesController {
	
	@Autowired
	private JokesServiceImpl jokesService;
	
	@GetMapping({"/jokes"})
	public List<JokesDto> getAll() {
		return jokesService.findAll();
	}
	
	@GetMapping({"/jokes/{id}"})
	public JokesDto getJokeById(@PathVariable int id) {
		JokesDto joke = jokesService.getById(id);
		return joke;
	}
	
}
