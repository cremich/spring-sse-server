package dev.cremich.sse.server.eventstream.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryDataStreamRepository {

  private Map<String, DataStream> persistedDataStreams;

  @PostConstruct
  void init() {
    persistedDataStreams = new HashMap<>();
  }

  /**
   * Add a new {@link DataStream} to the in-memory
   * @param dataStream the data stream entity
   */
  public void add(final DataStream dataStream) {
    persistedDataStreams.put(dataStream.getId().toString(), dataStream);
  }

  public DataStream findById(final String id) {
    return persistedDataStreams.get(id);
  }

  public Collection<DataStream> getAll() {
    return persistedDataStreams.values();
  }

  public void remove(final String id) {
    persistedDataStreams.remove(id);
  }
}
