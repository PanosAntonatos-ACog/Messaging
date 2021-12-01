package com.exercise.Rsocket.model;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import io.netty.util.internal.ThreadLocalRandom;

public class Bulletin {

	private String id;
	private String author;
	private String body;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate date;

	public static Bulletin fromException(Exception e) {
		Bulletin bulletin = new Bulletin();
		bulletin.setBody(e.getMessage());
		return bulletin;
	}

	public Bulletin() {
		super();
	}

	public Bulletin(String author, String body) {
		super();
		this.id = UUID.randomUUID().toString();
		this.author = author;
		this.body = body;
		this.date = getRandomDate();
	}

	public static Bulletin of(Bulletin bulletin) {
		return new Bulletin(bulletin.getAuthor(), bulletin.getBody());
	}

	private LocalDate getRandomDate() {
		ThreadLocalRandom r = ThreadLocalRandom.current();
		return LocalDate.of(r.nextInt(1990, 2020), r.nextInt(1, 13), r.nextInt(1, 29));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
