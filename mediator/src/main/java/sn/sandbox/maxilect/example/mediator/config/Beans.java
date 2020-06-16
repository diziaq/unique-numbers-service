package sn.sandbox.maxilect.example.mediator.config;


import java.util.function.LongSupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.sandbox.maxilect.example.mediator.impl.RangeRegistryService;
import sn.sandbox.maxilect.example.mediator.impl.RangeRegistryServiceImpl;
import sn.sandbox.maxilect.example.mediator.impl.misc.Variator;


@Configuration
class Beans {

  @Bean
  RangeRegistryService rangeRegistryService(
      @Value("${ranges.size.basic}") long rangeSizeBase,
      @Value("${ranges.size.variability}") int defaultSizeVariability,
      @Value("${ranges.initial.lower.bound}") long initialLowerBound) {

    LongSupplier rangeSizeSupplier = new Variator(rangeSizeBase, defaultSizeVariability);
    long normalInitialLowerBound = initialLowerBound > 0L ? initialLowerBound : 0L;

    return new RangeRegistryServiceImpl(rangeSizeSupplier, normalInitialLowerBound);
  }
}
