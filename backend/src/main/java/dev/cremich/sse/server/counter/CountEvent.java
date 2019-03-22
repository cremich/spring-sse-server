package dev.cremich.sse.server.counter;

class CountEvent {
  private final int newCount;

  CountEvent(int newCount) {
    this.newCount = newCount;
  }

  int getNewCount() {
    return newCount;
  }
}
