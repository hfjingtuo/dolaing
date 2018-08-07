package com.dolaing.core.common.exception;


/**
 * accessToken Token 错误
 * @author heli
 *
 */
public class TokenErrorException extends RuntimeException {

	public TokenErrorException(String msg) {
		super(msg);
	}
	public TokenErrorException(String msg,Throwable e) {
		super(msg, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1927281631604674505L;
	
	

}
