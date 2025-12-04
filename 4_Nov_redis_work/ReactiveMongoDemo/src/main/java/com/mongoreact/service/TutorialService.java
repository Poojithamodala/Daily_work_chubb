package com.mongoreact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.mongoreact.model.Tutorial;
import com.mongoreact.repository.TutorialRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TutorialService {
	@Autowired
	TutorialRepository tutorialRepository;

	public Flux<Tutorial> findAll() {
		return tutorialRepository.findAll();
	}

	public Flux<Tutorial> findByTitleContaining(String title) {
		return tutorialRepository.findByTitleContaining(title);
	}

	@Cacheable("tutorials")
	  public Mono<Tutorial> findById(String id) {
		    return tutorialRepository.findById(id);
	  }

	public Mono<Tutorial> save(Tutorial tutorial) {
		return tutorialRepository.save(tutorial);
	}

	public Mono<Tutorial> update(String id, Tutorial tutorial) {
		return tutorialRepository.findById(id).switchIfEmpty(Mono.error(new RuntimeException("Tutorial not found")))
				.flatMap(existingTutorial -> {
					existingTutorial.setTitle(tutorial.getTitle());
					existingTutorial.setDescription(tutorial.getDescription());
					existingTutorial.setPublished(tutorial.isPublished());
					return tutorialRepository.save(existingTutorial);
				});
	}

	  public Mono<Void> deleteById(String id) {
		    return tutorialRepository.deleteById(id);
	  }

	public Mono<Void> deleteAll() {
		return tutorialRepository.deleteAll();
	}

	public Flux<Tutorial> findByPublished(boolean isPublished) {
		return tutorialRepository.findByPublished(isPublished);
	}
}
