package com.mercadopago.utils;

import java.util.List;

public class ResponseError {

	private String message;
	private Integer status;
	private String error;
	private List<CauseError> cause;

	/**
	 * @return el message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message el message a establecer
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return el status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status el status a establecer
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return el error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error el error a establecer
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return el cause
	 */
	public List<CauseError> getCause() {
		return cause;
	}

	/**
	 * @param cause el cause a establecer
	 */
	public void setCause(List<CauseError> cause) {
		this.cause = cause;
	}
	
	@Override
	public String toString() {
		return "{message: " + message + ", status: " + status + ", error: " + error + ", cause: [" + cause + "]}";
	}

}
