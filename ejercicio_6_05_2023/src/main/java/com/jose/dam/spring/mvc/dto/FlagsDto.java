package com.jose.dam.spring.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.jose.dam.spring.mvc.entity.Flags;

public class FlagsDto implements Serializable {

	private static final long serialVersionUID = 1L;
	public int id;
	public String flag;
	public List<JokesDto> jokesesDto;

	public FlagsDto() {
	}

	public FlagsDto(int id, String flag) {
		this.id = id;
		this.flag = flag;
		this.jokesesDto = new ArrayList<>();
	}

	public FlagsDto(Flags flag) {
		this.id = flag.getId();
		this.flag = flag.getFlag();
		this.jokesesDto = null;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "Flag ID= " + id + " => " + flag;
	}

}
