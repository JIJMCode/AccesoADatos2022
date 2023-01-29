package com.jose.dam.spring.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.jose.dam.spring.mvc.entity.Categories;

public class CategoriesDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String category;
	public List<JokesDto> jokesesDto;

	public CategoriesDto() {
	}

	public CategoriesDto(int id, String category) {
		this.id = id;
		this.category = category;
		this.jokesesDto = new ArrayList<>();
	}
	
	public CategoriesDto(Categories category) {
		this.id = category.getId();
		this.category = category.getCategory();
		this.jokesesDto = null;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		String resultado = "Id: "  + id  + "\n";
		resultado += "Categoría: "  + category  + "\n";
		if (jokesesDto.size()>0) {
			resultado += "Listado de Id's de jokes con categoría " + category + ":\n";
			for (JokesDto jokeDto : jokesesDto) {
				resultado += jokeDto.getId() + "\n";
			} 
		} else {
			resultado += "Esta categoría no tiene ningún joke";
		}
		return resultado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriesDto other = (CategoriesDto) obj;
		return id == other.id;
	}
}
