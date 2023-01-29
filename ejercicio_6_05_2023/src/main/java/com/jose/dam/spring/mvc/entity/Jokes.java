package com.jose.dam.spring.mvc.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity
@Table(name = "jokes")
public class Jokes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer id;
	@ManyToOne
	@JoinColumn(name = "category_id")
	public Categories categories;
	@ManyToOne
	@JoinColumn(name = "type_id")
	public Types types;
	@ManyToOne
	@JoinColumn(name = "language_id")
	public Language language;
	@Column(name = "text1")
	public String text1;
	@Column(name = "text2")
	public String text2;
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "jokes_flags", joinColumns = {
            @JoinColumn(name = "joke_id", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "flag_id", nullable = false, updatable = false) })

	public Set<Flags> flagses = new HashSet<Flags>(0);

	public Jokes() {
	}

	public Jokes(Categories categories, Language language, Types types, String text1, String text2,
			Set<Flags> flagses) {
		this.categories = categories;
		this.language = language;
		this.types = types;
		this.text1 = text1;
		this.text2 = text2;
		this.flagses = flagses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Categories getCategories() {
		return this.categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Types getTypes() {
		return this.types;
	}

	public void setTypes(Types types) {
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
	@JsonIgnore
	public Set<Flags> getFlagses() {
		return this.flagses;
	}

	public void setFlagses(Set<Flags> flagses) {
		this.flagses = flagses;
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
