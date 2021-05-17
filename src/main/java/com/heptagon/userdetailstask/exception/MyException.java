package com.heptagon.userdetailstask.exception;

@SuppressWarnings("serial")
public class MyException extends RuntimeException{

    public MyException(MyException e){
        super(e);
    }

	public MyException(String message) {
		super(message);
	}

}