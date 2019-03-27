package dev.cremich.sse.server.eventstream.web.rest;

import dev.cremich.sse.server.eventstream.repository.DataStream;
import dev.cremich.sse.server.eventstream.repository.InMemoryDataStreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class DataStreamController {
  private InMemoryDataStreamRepository dataStreamRepository;

  @PostMapping("/streams")
  public DataStreamDto createADataStream() {
    DataStream dataStream = DataStream.create();
    dataStreamRepository.add(dataStream);

    return new DataStreamDto(dataStream.getId().toString());
  }

  @GetMapping("streams/{id}/data")
  public SseEmitter getEventStream(@PathVariable final String id) {
    DataStream dataStream = dataStreamRepository.findById(id);
    if (dataStream == null) {
      throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
    }

    return dataStream.getEmitter();
  }

  @Autowired
  public void setDataStreamRepository(final InMemoryDataStreamRepository dataStreamRepository) {
    this.dataStreamRepository = dataStreamRepository;
  }
}
