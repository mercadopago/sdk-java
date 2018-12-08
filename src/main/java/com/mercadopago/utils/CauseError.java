package com.mercadopago.utils;

public class CauseError {

	private String description;
	private String code;
	private String data;

	/**
	 * @return el description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description el description a establecer
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return el code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code el code a establecer
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return el data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data el data a establecer
	 */
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "{description: " + description + ", code: " + code + ", data: " + data + "}";
	}

}
