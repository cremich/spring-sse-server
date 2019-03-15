package dev.cremich.sse.server.counter;

import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
class CounterService {
  private static final Logger LOG = LoggerFactory.getLogger(CounterService.class);
  private ApplicationEventPublisher eventPublisher;
  private AtomicInteger counter;

  public CounterService() {
    counter = new AtomicInteger();
  }

  @Scheduled(fixedRate = 3000)
  void increaseCount() {
    CountEvent countEvent = new CountEvent(counter.incrementAndGet());
    LOG.info("increased counter to {}", countEvent.getNewCount());
    this.eventPublisher.publishEvent(countEvent);
  }

  @Autowired
  void setEventPublisher(final ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }
}
