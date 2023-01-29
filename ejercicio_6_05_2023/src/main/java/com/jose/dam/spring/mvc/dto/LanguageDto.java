package com.jose.dam.spring.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.jose.dam.spring.mvc.entity.Language;

public class LanguageDto implements Serializable {


	private static final long serialVersionUID = 1L;
	public Integer id;
	public String code;
	public String language;
	public List<JokesDto> jokesesDto;

	public LanguageDto() {
	}

	public LanguageDto(int id, String code, String language) {
		this.id = id;
		this.code = code;
		this.language = language;
		this.jokesesDto = new ArrayList<>();
	}
	
	public LanguageDto(Language language) {
		this.id = language.getId();
		this.code = language.getCode();
		this.language = language.getLanguage();
		this.jokesesDto = null;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

//	public List<JokesDto> getJokeses() {
//		return this.jokesesDto;
//	}
//
//	public void setJokeses(List<JokesDto> jokeses) {
//		this.jokesesDto = jokeses;
//	}

	@Override
	public String toString() {
		return "Id=" + id + " => " + language;
	}
}
