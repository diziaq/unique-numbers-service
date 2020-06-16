package sn.sandbox.maxilect.example.node.impl.service;


import reactor.core.publisher.Mono;


public interface ResponsivePool<T, E> {

  Item<T, E> item();

  void reportBroken(T itemId);

  interface Item<T, E> {

    T id();

    Mono<E> value();
  }
}
