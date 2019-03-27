package dev.cremich.sse.server.eventstream.repository;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class DataStream {

  private static final Logger LOG = LoggerFactory.getLogger(DataStream.class);

  private final UUID id;
  private SseEmitter emitter;

  private DataStream(final UUID id) {
    this.id = id;
  }

  public static DataStream create() {
    var dataStream = new DataStream(UUID.randomUUID());
    dataStream.createEmitter();
    return dataStream;
  }

  public UUID getId() {
    return id;
  }

  public SseEmitter getEmitter() {
    return emitter;
  }

  private void createEmitter() {
    emitter = new SseEmitter();
    emitter.onCompletion(() -> {
      LOG.info("emitter from stream {} completed.", this.getId());
    });
    emitter.onTimeout(() -> {
      this.createEmitter();
      LOG.info("emitter from stream {} timed out.", this.getId());
    });
  }
}
