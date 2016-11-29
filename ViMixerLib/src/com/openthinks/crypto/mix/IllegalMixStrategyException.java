package com.openthinks.crypto.mix;

/**
 * 
 * @author dailey.yet@outlook.com
 * @since v1.0
 */
public class IllegalMixStrategyException extends RuntimeException {

	private static final long serialVersionUID = 3870200131780755224L;

	public IllegalMixStrategyException() {
		super();
	}

	public IllegalMixStrategyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalMixStrategyException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalMixStrategyException(String message) {
		super(message);
	}

	public IllegalMixStrategyException(Throwable cause) {
		super(cause);
	}

}
