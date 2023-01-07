package com.jose.ejercicio_4_06_2022.Entidades;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.NamedNativeQuery;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Language generated by hbm2java
 */
@Entity
@Table(name = "language")
@NamedNativeQuery(
	    name = "find_language_id_and_language",
	    query = "SELECT id, language " + "FROM Language "
	)
public class Language implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String language;
	private Set<Jokes> jokeses = new HashSet<Jokes>(0);

	public Language() {
	}

	public Language(int id) {
		this.id = id;
	}

	public Language(int id, String code, String language, Set<Jokes> jokeses) {
		this.id = id;
		this.code = code;
		this.language = language;
		this.jokeses = jokeses;
	}

	@Id
	@SequenceGenerator(name="seqLang", sequenceName="seq_languages", allocationSize=1, initialValue = 1)
	@GeneratedValue(generator="seqLang")
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "code", length = 2)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "language")
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "language")
	public Set<Jokes> getJokeses() {
		return this.jokeses;
	}

	public void setJokeses(Set<Jokes> jokeses) {
		this.jokeses = jokeses;
	}

}

