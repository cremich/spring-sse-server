<template>
  <div class="count" v-on:click="raiseCount">
    <div class="count-container">
      <h1>{{ count }}</h1>
      <label>Touch or click to raise the count</label>
    </div>
  </div>
</template>

<script>
import counterClient from "../api/counter/client";
import streamClient from "../api/stream/client";
import eventListenerMixin from "../mixins/eventListener";

export default {
  name: "Counter",
  mixins: [eventListenerMixin],
  data() {
    return {
      count: 0,
      streamId: 0,
      eventSource: null
    };
  },
  beforeMount() {
    this.registerEventSource();
  },
  methods: {
    registerEventSource: async function() {
      await this.createStream();
      this.addEventListener();
    },
    createStream: async function() {
      const response = await streamClient.createStream();
      this.streamId = response.data.id;
    },
    raiseCount: async function() {
      counterClient.increaseCount();
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.count {
  height: 100%;
  width: 100%;
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
.count-container {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  width: 50%;
  height: 50%;
}
h1 {
  margin: 0;
  font-size: 13rem;
}
label {
  font-size: 1.5rem;
}
</style>
