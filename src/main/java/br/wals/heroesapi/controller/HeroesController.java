package br.wals.heroesapi.controller;

import br.wals.heroesapi.document.Heroes;
import br.wals.heroesapi.service.HeroesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.wals.heroesapi.constants.HeroesConstant.HEROES_ENDPOINT_LOCAL;

@RestController
@RequestMapping(HEROES_ENDPOINT_LOCAL)
@Slf4j
public class HeroesController {

//    private static final Logger log = LoggerFactory.getLogger(HeroesController.class);
    private HeroesService service;

    public HeroesController(HeroesService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Heroes> listAll() {
        log.info("Request all heroes");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Heroes>> findById(@PathVariable("id") String id) {
        log.info("Request hero with id {}", id);
        return service.findById(id)
                .map( item -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
        log.info("Create a new hero");
        return service.save(heroes);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHero(@PathVariable("id") String id) {
        log.info("Delete a hero with id {}", id);
        service.deleteById(id);
    }
}
