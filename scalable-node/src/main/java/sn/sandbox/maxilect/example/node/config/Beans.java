package sn.sandbox.maxilect.example.node.config;


import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;
import sn.sandbox.maxilect.example.node.impl.NumbersGeneratorService;
import sn.sandbox.maxilect.example.node.impl.service.CountersFromWebLocation;
import sn.sandbox.maxilect.example.node.impl.service.NumbersGeneratorServiceImpl;
import sn.sandbox.maxilect.example.node.impl.service.PrudentPoolOfCounters;


@Configuration
class Beans {

  @Bean
  NumbersGeneratorService numbersGeneratorService(
      @Value("${ranges.source.location}") String rangesSourceLocation,
      @Value("${ranges.source.retry.attempts}") long rangesSourceRetryAttempts,
      @Value("${ranges.source.retry.delay.milliseconds}") long rangesSourceDelayMilliseconds
  ) {

    var countersSupplier = new CountersFromWebLocation(rangesSourceLocation);
    var countersPool = new PrudentPoolOfCounters(countersSupplier);
    var retry = Retry.fixedDelay(rangesSourceRetryAttempts, Duration.ofMillis(rangesSourceDelayMilliseconds));

    return new NumbersGeneratorServiceImpl(countersPool, retry);
  }
}
