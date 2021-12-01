package com.exercise.Rsocket.model;

public class BulletinRequest {

	private String author;

	public BulletinRequest() {
	}

	public BulletinRequest(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
