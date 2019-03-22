package dev.cremich.sse.server.eventstream.web.rest;

import dev.cremich.sse.server.eventstream.repository.DataStream;
import dev.cremich.sse.server.eventstream.repository.InMemoryDataStreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class DataStreamController {

  private InMemoryDataStreamRepository dataStreamRepository;

  @PostMapping("/streams")
  public DataStreamDto createADataStream() {
    DataStream dataStream = DataStream.create();
    dataStreamRepository.add(dataStream);

    SseEmitter emitter = dataStream.getEmitter();
    emitter.onCompletion(() -> dataStreamRepository.remove(dataStream.getId().toString()));
    emitter.onTimeout(() -> dataStreamRepository.remove(dataStream.getId().toString()));

    return new DataStreamDto(dataStream.getId().toString());
  }

  @GetMapping("streams/{id}/data")
  public SseEmitter getEventStream(@PathVariable final String id) {
    DataStream dataStream = dataStreamRepository.findById(id);
    return dataStream.getEmitter();
  }

  @Autowired
  public void setDataStreamRepository(final InMemoryDataStreamRepository dataStreamRepository) {
    this.dataStreamRepository = dataStreamRepository;
  }
}
