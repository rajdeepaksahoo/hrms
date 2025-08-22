package org.vrnda.hrms.controller;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_DEFAULT)
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean status;
	private String message;
	private String error;
	@JsonIgnore
	private int code;
	private String detailMessage;
	private int identifier;
	private Object data;

	public Message() {

	}

	/**
	 * Instantiates a new message with the given message builder.
	 *
	 * @param builder the builder
	 */
	private Message(MessageBuilder builder) {
		this.message = builder.message;
		this.error = builder.error;
		this.status = builder.status;
		this.code = builder.code;
		this.detailMessage = builder.detailMessage;
		this.identifier = builder.identifier;
		this.data = builder.data;
	}

	/**
	 * A class that creates an instance of {@link Message} with the properties that are not null.
	 */
	@JsonIgnoreType
	public static class MessageBuilder {

		private String message;
		private String error;
		private Boolean status;
		private int code;
		private String detailMessage;
		private int identifier;
		private Object data;

		/**
		 * Sets message.
		 *
		 * @param message the message
		 * @return the message builder
		 */
		public MessageBuilder message(String message) {
			this.message = message; 
			return this;
		}

		/**
		 * Sets an error.
		 *
		 * @param error the error
		 * @return the message builder
		 */
		public MessageBuilder error(String error) {
			this.error = error; 
			return this;
		}

		/**
		 * Sets status.
		 *
		 * @param status the status
		 * @return the message builder
		 */
		public MessageBuilder status(Boolean status) {
			this.status = status; 
			return this;
		}

		/**
		 * Sets code.
		 *
		 * @param code the code
		 * @return the message builder
		 */
		public MessageBuilder code(int code) {
			this.code = code; 
			return this;
		}

		/**
		 * Sets detailMessage.
		 *
		 * @param detailMessage the detail message
		 * @return the message builder
		 */
		public MessageBuilder detailMessage(String detailMessage) {
			this.detailMessage = detailMessage; 
			return this;
		}

		/**
		 * Sets identifier.
		 *
		 * @param identifier the identifier
		 * @return the message builder
		 */
		public MessageBuilder identifier(int identifier) {
			this.identifier = identifier; 
			return this;
		}

		public MessageBuilder data(Object data) {
			this.data = data; 
			return this;
		}

		/**
		 * Builds a message object.
		 *
		 * @return the message
		 */
		public Message build() {
			return new Message(this);
		}

	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * Get the status code associated with the response.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<?> data) {
		this.data = data;
	}
}
