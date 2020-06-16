package sn.sandbox.maxilect.example.node.impl.service;


import java.util.Map;
import java.util.function.Supplier;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sn.sandbox.maxilect.example.node.impl.counter.LongCounter;


public final class CountersFromWebLocation implements Supplier<Mono<LongCounter>> {

  final WebClient webClient;

  public CountersFromWebLocation(String rangesSourceLocation) {
    webClient = WebClient.create(rangesSourceLocation);
  }

  @Override
  public Mono<LongCounter> get() {
    return webClient
               .get()
               .exchange()
               .flatMap(cr -> cr.bodyToMono(Map.class))
               .map(LongCounter::fromMinMaxMap);
  }
}
