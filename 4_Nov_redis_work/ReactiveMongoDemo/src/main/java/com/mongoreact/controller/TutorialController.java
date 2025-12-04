package com.mongoreact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongoreact.model.Tutorial;
import com.mongoreact.service.TutorialService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TutorialController {
	@Autowired
	  TutorialService tutorialService;
	
	  @PostMapping("/tutorials")
	  @ResponseStatus(HttpStatus.CREATED)
	  public Mono<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
	    return tutorialService.save(tutorial);
	  }
	  
	  @GetMapping("/tutorials")
	  @ResponseStatus(HttpStatus.OK)
	  public Flux<Tutorial> getAllTutorials(@RequestParam(required = false) String title) {
	    if (title == null)
	      return tutorialService.findAll();
	    else
	      return tutorialService.findByTitleContaining(title);
	  }

	  @GetMapping("/tutorials/{id}")
	  @ResponseStatus(HttpStatus.OK)
	  public Mono<Tutorial> getTutorialById(@PathVariable String id) {
	    return tutorialService.findById(id);
	  }
	  
	  @GetMapping("/tutorials/published")
	  @ResponseStatus(HttpStatus.OK)
	  public Flux<Tutorial> findByPublished() {
	    return tutorialService.findByPublished(true);
	  }
	  
	  @PutMapping("/tutorials/{id}")
	  @ResponseStatus(HttpStatus.OK)
	  public Mono<Tutorial> updateTutorial(@PathVariable String id, @RequestBody Tutorial tutorial) {
	    return tutorialService.update(id, tutorial);
	  }

	  @DeleteMapping("/tutorials/{id}")
	  @ResponseStatus(HttpStatus.NO_CONTENT)
	  public Mono<Void> deleteTutorial(@PathVariable String id) {
	    return tutorialService.deleteById(id);
	  }

	  @DeleteMapping("/tutorials")
	  @ResponseStatus(HttpStatus.NO_CONTENT)
	  public Mono<Void> deleteAllTutorials() {
	    return tutorialService.deleteAll();
	  }
}
