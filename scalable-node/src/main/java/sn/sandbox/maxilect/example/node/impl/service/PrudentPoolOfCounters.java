package sn.sandbox.maxilect.example.node.impl.service;


import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import reactor.core.publisher.Mono;
import sn.sandbox.maxilect.example.node.impl.counter.LongCounter;


public final class PrudentPoolOfCounters implements ResponsivePool<UUID, LongCounter> {

  private final ReentrantLock lock = new ReentrantLock(false);
  private final Supplier<Mono<LongCounter>> supplier;
  private Item<UUID, LongCounter> currentlyActive;

  public PrudentPoolOfCounters(Supplier<Mono<LongCounter>> supplier) {
    this.supplier = supplier;
    this.currentlyActive = makeNew();
  }

  @Override
  public Item<UUID, LongCounter> item() {
    return currentlyActive;
  }

  @Override
  public void reportBroken(UUID itemId) {
    try {
      lock.lock();

      if (isActive(itemId)) {
        this.currentlyActive = makeNew();
      } else {
        // nothing to do
      }
    } finally {
      lock.unlock();
    }
  }

  private Item<UUID, LongCounter> makeNew() {
    Mono<LongCounter> longCounterMono = supplier.get().cache();
    UUID uuid = UUID.randomUUID();

    return new Item<>() {
      @Override
      public UUID id() {
        return uuid;
      }

      @Override
      public Mono<LongCounter> value() {
        return longCounterMono;
      }
    };
  }

  private boolean isActive(UUID itemId) {
    return currentlyActive.id().equals(itemId);
  }
}
