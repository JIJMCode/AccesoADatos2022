package com.jose.ejercicio_2_15_2022_Entidades;

import java.util.List;

public class RootLanguages {
	private List<String> jokeLanguages;
	private List<Language> possibleLanguages;

	/**
	 * @return the languages
	 */
	public List<String> getLanguages() {
		return jokeLanguages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(List<String> languages) {
		this.jokeLanguages = languages;
	}

	/**
	 * @return the possibleLanguages
	 */
	public List<Language> getPossibleLanguages() {
		return possibleLanguages;
	}

	/**
	 * @param possibleLanguages the possibleLanguages to set
	 */
	public void setPossibleLanguages(List<Language> possibleLanguages) {
		this.possibleLanguages = possibleLanguages;
	}


}
