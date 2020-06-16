package sn.sandbox.maxilect.example.mediator.impl;


import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RangeController {

  private final RangeRegistryService service;

  public RangeController(RangeRegistryService service) {
    this.service = service;
  }

  @GetMapping("/range")
  public Object nextNumber() {
    var range = service.nextRange();
    return Map.of("min", range.min(), "max", range.max());
  }
}
