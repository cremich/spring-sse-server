package dev.cremich.sse.server.eventstream.web.rest;

class DataStreamDto {

  private final String id;

  DataStreamDto(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
