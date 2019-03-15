package dev.cremich.sse.server.eventstream.web.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.cremich.sse.server.eventstream.repository.DataStream;
import dev.cremich.sse.server.eventstream.repository.InMemoryDataStreamRepository;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DataStreamControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private HttpMessageConverter<?>[] httpMessageConverters;

  @Autowired
  InMemoryDataStreamRepository repository;

  private MockMvc restMvc;

  @BeforeEach
  void setUp() {
    DataStreamController controller = new DataStreamController();
    controller.setDataStreamRepository(repository);
    this.restMvc = MockMvcBuilders.standaloneSetup(controller)
        .setMessageConverters(httpMessageConverters)
        .build();
  }

  @Test
  @DisplayName("Create a new data stream")
  void createADataStream() throws Exception {
    this.restMvc.perform(post("/streams").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNotEmpty());
  }

  @Test
  @DisplayName("Receive server sent events via http client")
  void getEventStream() throws Exception {
    DataStream dataStream = DataStream.create();
    repository.add(dataStream);

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:" + port + "/streams/" + dataStream.getId().toString() + "/data"))
        .timeout(Duration.ofMinutes(1))
        .header("Content-Type", "text/event-stream")
        .GET()
        .build();

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<Stream<String>> response = client.send(request, BodyHandlers.ofLines());
    final Optional<String> firstServerSentEvent = response.body().findFirst();

    assertFalse(firstServerSentEvent.isEmpty());
    assertTrue(firstServerSentEvent.get().contains("data:2"));
    assertEquals(200, response.statusCode());
  }
}
