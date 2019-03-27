package dev.cremich.sse.server.counter.web.rest;

import dev.cremich.sse.server.counter.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

  private CounterService counterService;

  @PostMapping(value = "/counters/increaseOrder", produces = "text/plain")
  public void increaseCounter() {
    counterService.increaseCount();
  }

  @Autowired
  public void setCounterService(CounterService counterService) {
    this.counterService = counterService;
  }
}
