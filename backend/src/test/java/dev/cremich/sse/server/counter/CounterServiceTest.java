package dev.cremich.sse.server.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class CounterServiceTest {

  @Mock
  ApplicationEventPublisher mockedEventPublisher;

  @Test
  @DisplayName("Counter is increased")
  void increaseCount() {
    var service = new CounterService();
    service.setEventPublisher(mockedEventPublisher);
    assertEquals(1, service.increaseCount());
  }
}
