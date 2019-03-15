package dev.cremich.sse.server.eventstream.repository;

import java.util.UUID;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class DataStream {

  private final UUID id;
  private final SseEmitter emitter;

  private DataStream(final UUID id, final SseEmitter emitter) {
    this.id = id;
    this.emitter = emitter;
  }

  public static DataStream create() {
    return new DataStream(UUID.randomUUID(), new SseEmitter());
  }

  public UUID getId() {
    return id;
  }

  public SseEmitter getEmitter() {
    return emitter;
  }
}
