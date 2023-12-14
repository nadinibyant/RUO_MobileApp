package com.example.ruo.pojo.Therapy;

public class DetailTherapyResponse{
	private boolean success;
	private DataTherapy dataTherapy;
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public DataTherapy getDataTherapy(){
		return dataTherapy;
	}

	public String getMessage(){
		return message;
	}
}
