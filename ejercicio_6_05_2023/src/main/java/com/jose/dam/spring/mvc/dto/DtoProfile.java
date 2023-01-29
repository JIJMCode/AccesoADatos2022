package com.jose.dam.spring.mvc.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.jose.dam.spring.mvc.entity.Categories;
import com.jose.dam.spring.mvc.entity.Flags;
import com.jose.dam.spring.mvc.entity.Jokes;
import com.jose.dam.spring.mvc.entity.Language;
import com.jose.dam.spring.mvc.entity.Types;

public class DtoProfile {

	/*
	 * Mapeo de Jokes 
	*/
	public static JokesDto mapJokesToJokesDto(Jokes joke) {
		JokesDto jokeDto = new JokesDto(joke);
		if (joke.getCategories() != null) {
			jokeDto.categories = new CategoriesDto(joke.getCategories());
		}
		if (joke.getLanguage() != null) {
			jokeDto.language = new LanguageDto(joke.getLanguage());
		}
		if (joke.getTypes() != null) {
			jokeDto.types = new TypesDto(joke.getTypes());
		}
		
		jokeDto.flagsesDto = mapFlagsesToflagsesDto(joke.getFlagses());

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
	 * Mapeo de Language 
	*/
	public static LanguageDto mapLanguageToLanguageDto(Language language) {
		LanguageDto languageDto = new LanguageDto(language);
		languageDto.jokesesDto = mapJokesesToJokesesDto(language.getJokeses());
		
		return languageDto;
	}
	
	/*
	 * Mapeo de Types 
	*/
	public static TypesDto mapTypesToTypesDto(Types type) {
		TypesDto typeDto = new TypesDto(type);
		typeDto.jokesesDto = mapJokesesToJokesesDto(type.getJokeses());
		
		return typeDto;
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
			JokesDto jokeDto = mapJokesToJokesDto(e);
			if (e.getCategories() != null) {
				jokeDto.categories = new CategoriesDto(e.getCategories());
			}
			if (e.getLanguage() != null) {
				jokeDto.language = new LanguageDto(e.getLanguage());
			}
			if (e.getTypes() != null) {
				jokeDto.types = new TypesDto(e.getTypes());
			}

			jokesesDto.add(jokeDto);
		});
		
		return jokesesDto;
	}
}
