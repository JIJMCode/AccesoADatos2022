package com.jose.dam.spring.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jose.dam.spring.mvc.entity.Categories;
import com.jose.dam.spring.mvc.entity.Jokes;
import com.jose.dam.spring.mvc.entity.Language;
import com.jose.dam.spring.mvc.entity.Types;

public class JokesDto implements Serializable {

	private static final long serialVersionUID = 1L;
	public int id;
	public CategoriesDto categories;
	public LanguageDto language;
	public TypesDto types;
	public String text1;
	public String text2;
	public List<FlagsDto> flagsesDto;

	public JokesDto() {
	}

	public JokesDto(int id, CategoriesDto categories, LanguageDto language, TypesDto types, String text1, String text2) {
		this.id = id;
		this.categories = categories;
		this.language = language;
		this.types = types;
		this.text1 = text1;
		this.text2 = text2;
		this.flagsesDto = new ArrayList<>();
	}

	public JokesDto(Jokes joke) {
		this.id = joke.id;
		this.categories = null;
		this.language = null;
		this.types = null;
		this.text1 = joke.text1;
		this.text2 = joke.text2;
		this.flagsesDto = null;
	}
	
//	public JokesDto(Jokes joke) {
//		this.id = joke.id;
//		this.categories = joke.categories;
//		this.language = joke.language;
//		this.types = joke.types;
//		this.text1 = joke.text1;
//		this.text2 = joke.text2;
//		this.flagsesDto = null;
//	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CategoriesDto getCategories() {
		return this.categories;
	}

	public void setCategories(CategoriesDto categories) {
		this.categories = categories;
	}

	public LanguageDto getLanguage() {
		return this.language;
	}

	public void setLanguage(LanguageDto language) {
		this.language = language;
	}

	public TypesDto getTypes() {
		return this.types;
	}

	public void setTypes(TypesDto types) {
		this.types = types;
	}

	public String getText1() {
		return this.text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return this.text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	@Override
	public String toString() {
		String categoriesValidated = this.categories != null ? this.categories.getCategory() : "sin datos";
		String languageValidated = this.language != null ? this.language.getLanguage() : "sin datos";
		String typeValidated = this.types != null ? this.language.getLanguage() : "sin datos";
		String text1validated = text1 != null ? text1 : "sin datos";
		String text2validated = text2 != null ? text2 : "sin datos";
		return "*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\nJoke (id=" + id + ")\ncategory=" + categoriesValidated +
				", language=" + languageValidated + ", type=" + typeValidated
				+ ",\ntext1=" + text1validated + ",\ntext2=" + text2validated + "\n" ;
	}
}
