package com.exercise.Rsocket.service;

import java.time.Duration;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.exercise.Rsocket.model.Bulletin;
import com.exercise.Rsocket.repository.BulletinRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BulletinService {

	private final BulletinRepository repository;

	public BulletinService(BulletinRepository repository) {
		this.repository = repository;
	}

	private static final HashMap<String, Bulletin> bulletins = new HashMap<String, Bulletin>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("panos", new Bulletin("Panos", "Talk is cheap. Show me the code."));
			put("tade", new Bulletin("Tade", "Truth can only be found in one place: the code."));
			put("martinfowler", new Bulletin("Martin Fowler",
					"Any fool can write code that a computer can understand. Good programmers write code that humans can understand."));
		}
	};

	public Flux<Bulletin> getByAuthor(String author) {
		return Flux.interval(Duration.ZERO, Duration.ofSeconds(1)).map(i -> Bulletin.of(bulletins.get(author)));
	}

	public Flux<Bulletin> getAll(String author) {
		return repository.getAll(author);
	}

	public Mono<Bulletin> getOne(String author) {
		return repository.getOne(author);
	}

	public void add(Bulletin bulletin) {
		repository.add(bulletin);
	}
}
