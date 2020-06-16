package sn.sandbox.maxilect.example.node.impl;


import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class IdController {

  private final NumbersGeneratorService service;

  public IdController(NumbersGeneratorService service) {
    this.service = service;
  }

  @GetMapping("/id")
  public Mono<Object> nextNumber() {
    return service.nextNumber().map(x -> Map.of("id", x));
  }
}
