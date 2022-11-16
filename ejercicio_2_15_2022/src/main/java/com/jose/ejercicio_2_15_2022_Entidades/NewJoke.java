package com.jose.ejercicio_2_15_2022_Entidades;

import java.util.ArrayList;

public class NewJoke {
	private int id;
	private int category;
	private int type;
	private String setup;
	private String joke;
	private String delivery;
	private int lang;
	private ArrayList<Flag> Flags;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the setup
	 */
	public String getSetup() {
		return setup;
	}
	/**
	 * @param setup the setup to set
	 */
	public void setSetup(String setup) {
		this.setup = setup;
	}
	/**
	 * @return the joke
	 */
	public String getJoke() {
		return joke;
	}
	/**
	 * @param joke the joke to set
	 */
	public void setJoke(String joke) {
		this.joke = joke;
	}
	/**
	 * @return the delivery
	 */
	public String getDelivery() {
		return delivery;
	}
	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	/**
	 * @return the lang
	 */
	public int getLang() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLang(int lang) {
		this.lang = lang;
	}
	/**
	 * @return the flags
	 */
	public ArrayList<Flag> getFlags() {
		return Flags;
	}
	/**
	 * @param flags the flags to set
	 */
	public void setFlags(ArrayList<Flag> flags) {
		Flags = flags;
	}
}
