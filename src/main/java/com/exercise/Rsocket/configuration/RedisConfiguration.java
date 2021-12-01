package com.exercise.Rsocket.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.exercise.Rsocket.model.Bulletin;

@Configuration
public class RedisConfiguration {

	@Bean
	public ReactiveRedisOperations<String, Bulletin> redisOperations(ReactiveRedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<Bulletin> serializer = new Jackson2JsonRedisSerializer<>(Bulletin.class);

		RedisSerializationContext.RedisSerializationContextBuilder<String, Bulletin> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());

		RedisSerializationContext<String, Bulletin> context = builder.value(serializer)
				.hashKey(new StringRedisSerializer()).hashValue(serializer).build();

		return new ReactiveRedisTemplate<>(factory, context);
	}

}
