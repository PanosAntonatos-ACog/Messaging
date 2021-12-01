package com.exercise.Rsocket.controller;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.exercise.Rsocket.model.Bulletin;
import com.exercise.Rsocket.model.BulletinRequest;
import com.exercise.Rsocket.service.BulletinService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class BulletinSocketController {

	private final BulletinService service;

	public BulletinSocketController(BulletinService service) {
		this.service = service;
	}

	@MessageMapping("tweets.by.author")
	public Flux<Bulletin> getByAuthor(BulletinRequest request) {
		return service.getByAuthor(request.getAuthor());
	}

	@MessageMapping("currentBulletinData")
	public Mono<Bulletin> currentBulletinData(BulletinRequest bulletinRequest) {
		return service.getOne(bulletinRequest.getAuthor());
	}

	@MessageMapping("feedBulletinData")
	public Flux<Bulletin> feedBulletinData(BulletinRequest bulletinRequest) {
		return service.getAll(bulletinRequest.getAuthor());
	}

	@MessageMapping("collectBulletinData")
	public Mono<Void> collectBulletinData(Bulletin bulletinData) {
		service.add(bulletinData);
		return Mono.empty();
	}

	@MessageExceptionHandler
	public Mono<Bulletin> handleException(Exception e) {
		return Mono.just(Bulletin.fromException(e));
	}

}
