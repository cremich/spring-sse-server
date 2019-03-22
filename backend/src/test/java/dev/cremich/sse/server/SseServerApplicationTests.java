package dev.cremich.sse.server;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.cremich.sse.server.eventstream.web.rest.DataStreamController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SseServerApplicationTests {

  @Autowired
  DataStreamController controller;

  @Test
  @DisplayName("Spring context is loaded")
  void contextLoads() {
    assertNotNull(controller);
  }

}
