package sn.sandbox.maxilect.example.node.impl.service;


import java.util.UUID;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import sn.sandbox.maxilect.example.node.impl.NumbersGeneratorService;
import sn.sandbox.maxilect.example.node.impl.counter.LongCounter;


public final class NumbersGeneratorServiceImpl implements NumbersGeneratorService {

  private final Mono<Long> publisher;

  public NumbersGeneratorServiceImpl(
      ResponsivePool<UUID, LongCounter> countersPool,
      Retry retryStrategy
  ) {
    this.publisher = Mono
                         .defer(
                             () -> {
                               var item = countersPool.item();
                               return item.value()
                                          .map(LongCounter::next)
                                          .doOnError(e -> countersPool.reportBroken(item.id()));
                             })
                         .retryWhen(retryStrategy);
  }

  @Override
  public Mono<Long> nextNumber() {
    return publisher;
  }
}
