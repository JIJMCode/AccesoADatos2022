package com.jose.dam.spring.mvc.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jose.dam.spring.mvc.entity.Categories;
import com.jose.dam.spring.mvc.entity.Flags;
import com.jose.dam.spring.mvc.entity.Jokes;

public class DtoProfile {

	/*
	 * Mapeo de Jokes 
	*/
	public static JokesDto mapJokesToJokesDto(Jokes joke) {
		JokesDto jokeDto = new JokesDto(joke);
		jokeDto.flagsesDto = mapFlagsesToflagsesDto(joke.flagses);
		
		return jokeDto;
	}
	
	/*
	 * Mapeo de Flags 
	*/
	public static FlagsDto mapFlagsToFlagsDto(Flags flag) {
		FlagsDto flagDto = new FlagsDto(flag);
		flagDto.jokesesDto = mapJokesesToJokesesDto(flag.getJokeses());
		
		return flagDto;
	}
	
	/*
	 * Mapeo de Categories 
	*/
	public static CategoriesDto mapCategoriesToCategoriesDto(Categories category) {
		CategoriesDto categoryDto = new CategoriesDto(category);
		categoryDto.jokesesDto = mapJokesesToJokesesDto(category.getJokeses());
		
		return categoryDto;
	}
	
	/*
	 * Mapeo de Listas
	 */
	public static List<FlagsDto> mapFlagsesToflagsesDto (Set<Flags> flagses) {
		List<FlagsDto> flagsesDto = new ArrayList<>();
		
		flagses.forEach(e-> {
			FlagsDto flagDto = new FlagsDto(e);
			flagsesDto.add(flagDto);
		});
		
		return flagsesDto;
	}
	
	public static List<JokesDto> mapJokesesToJokesesDto (Set<Jokes> jokeses) {
		List<JokesDto> jokesesDto = new ArrayList<>();
		
		jokeses.forEach(e-> {
			JokesDto jokeDto = new JokesDto(e);
			jokesesDto.add(jokeDto);
		});
		
		return jokesesDto;
	}
}
