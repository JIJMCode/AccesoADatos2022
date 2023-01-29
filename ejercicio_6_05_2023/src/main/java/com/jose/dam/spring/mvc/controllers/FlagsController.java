package com.jose.dam.spring.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jose.dam.spring.mvc.dto.FlagsDto;
import com.jose.dam.spring.mvc.entity.Flags;
import com.jose.dam.spring.mvc.entity.Jokes;
import com.jose.dam.spring.mvc.services.FlagsServiceImpl;
import com.jose.dam.spring.mvc.services.IFlagsService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class FlagsController {

	@Autowired
	private FlagsServiceImpl flagsService;
	
	@GetMapping({"/flags"})
	public List<FlagsDto> index() {
		return flagsService.findAll();
	}
	
	@GetMapping({"/flags/{id}"})
	public FlagsDto getJokeById(@PathVariable int id) {
		FlagsDto flag = flagsService.getById(id);
		return flag;
	}
}
