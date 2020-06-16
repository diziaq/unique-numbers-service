package sn.sandbox.maxilect.example.node.impl;


import reactor.core.publisher.Mono;


public interface NumbersGeneratorService {

  Mono<Long> nextNumber();
}
