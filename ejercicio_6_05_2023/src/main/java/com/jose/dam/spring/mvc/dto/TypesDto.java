package com.jose.dam.spring.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.jose.dam.spring.mvc.entity.Types;

public class TypesDto implements Serializable{

	private static final long serialVersionUID = 1L;
	public Integer id;
	public String type;
	public List<JokesDto> jokesesDto;

	public TypesDto() {
	}

	public TypesDto(String type) {
		this.type = type;
	}

	public TypesDto(int id, String type) {
		this.id = id;
		this.type = type;
		this.jokesesDto = new ArrayList<>();
	}
	
	public TypesDto(Types type) {
		this.id = type.getId();
		this.type = type.getType();
		this.jokesesDto = null;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
