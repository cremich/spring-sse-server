package dev.cremich.sse.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class SseServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SseServerApplication.class, args);
  }

}
