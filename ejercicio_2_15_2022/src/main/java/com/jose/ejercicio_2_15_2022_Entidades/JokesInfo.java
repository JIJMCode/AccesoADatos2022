package com.jose.ejercicio_2_15_2022_Entidades;

import java.util.List;

public class JokesInfo {
	private List<String> categories;
	private List<String> flags;
	private List<String> types;
	private List<LanguageRange> safeJokes;

	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @return the flags
	 */
	public List<String> getFlags() {
		return flags;
	}

	/**
	 * @param flags the flags to set
	 */
	public void setFlags(List<String> flags) {
		this.flags = flags;
	}

	/**
	 * @return the types
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(List<String> types) {
		this.types = types;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	/**
	 * @return the safeJokes
	 */
	public List<LanguageRange> getSafeJokes() {
		return safeJokes;
	}

	/**
	 * @param safeJokes the safeJokes to set
	 */
	public void setSafeJokes(List<LanguageRange> safeJokes) {
		this.safeJokes = safeJokes;
	}
}
