package com.exercise.Rsocket.repository;

import java.time.Duration;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;

import com.exercise.Rsocket.model.Bulletin;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BulletinRepository {

	private static Logger logger = LoggerFactory.getLogger(BulletinRepository.class);

	private final ReactiveRedisOperations<String, Bulletin> redisTemplate;

	public BulletinRepository(ReactiveRedisOperations<String, Bulletin> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public Mono<Void> save(Bulletin bulletin) {
		return Mono.when(redisTemplate.<String, Bulletin>opsForHash().put("bulletins", bulletin.getId(), bulletin),
				redisTemplate.opsForZSet().add(bulletin.getAuthor().toLowerCase().replaceAll("\\s", ""), bulletin,
						bulletin.getDate().toEpochDay()))
				.then();
	}

	public Flux<Bulletin> getByAuthor(String author) {
		return redisTemplate.opsForZSet().reverseRange(author, Range.unbounded());
	}

	public Flux<Bulletin> getAll(String author) {
		return Flux.fromStream(Stream.generate(() -> getBulletinResponse(author))).log()
				.delayElements(Duration.ofSeconds(1));
	}

	public Mono<Bulletin> getOne(String author) {
		return Mono.just(getBulletinResponse(author));
	}

	public void add(Bulletin bulletin) {
		logger.info("New bulletin data: {}", bulletin);
	}

	private Bulletin getBulletinResponse(String body) {
		return new Bulletin();
	}

}
