package com.exercise.Rsocket.controller;

import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.exercise.Rsocket.model.Bulletin;
import com.exercise.Rsocket.model.BulletinRequest;
import com.exercise.Rsocket.service.BulletinService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class BulletinRestController {

	private final Mono<RSocketRequester> requester;
	private BulletinService service;

	public BulletinRestController(BulletinService service, Mono<RSocketRequester> requester) {
		this.requester = requester;
		this.service = service;
	}

//	@GetMapping(value = "/current/{stock}")
//	public Publisher<Bulletin> current(@PathVariable("author") String author) {
//		return requester.route("currentBulletinData").data(new BulletinRequest(author))
//				.retrieveMono(Bulletin.class);
//	}

	@GetMapping(value = "/bulletin/{author}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Bulletin> getByAuthor(@PathVariable String author) {
		return service.getByAuthor(author);
	}

	@GetMapping(value = "/socket/{author}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Bulletin> getByAuthorViaSocket(@PathVariable String author) {
		return requester.flatMapMany(
				r -> r.route("tweets.by.author").data(new BulletinRequest(author)).retrieveFlux(Bulletin.class));
	}

//	@GetMapping(value = "/collect")
//	public Publisher<Void> collect() {
//		return rSocketRequester.route("collectBulletinData").data(getMarketData()).send();
//	}

}
