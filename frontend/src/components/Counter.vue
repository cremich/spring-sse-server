<template>
  <div class="count" v-on:click="raiseCount">
    <div class="count-container">
      <h1>{{ count }}</h1>
      <label>Touch or click to raise the count</label>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Counter",
  data() {
    return {
      count: 0,
      streamId: 0,
      eventSource: null,
    };
  },
  beforeMount() {
    this.registerEventSource();
  },
  methods: {
    registerEventSource: function() {
      this.createStream().then(() => {
        this.addEventListener();
      });
    },
    createStream: function() {
      var self = this;
      return axios
        .post(`/streams`)
        .then(response => {
          self.streamId = response.data.id;
        })
        .catch(e => {
          console.log(e);
        });
    },
    addEventListener: function() {
      let self = this;
      this.eventSource = new EventSource("/streams/" + this.streamId + "/data");
      this.eventSource.addEventListener(
        "count",
        function(e) {
          self.count = e.data;
        },
        false
      );

      this.eventSource.onerror = function(e) {
        console.log(e);
      };
    },
    raiseCount: async function() {
      axios.post(`/counters/increaseOrder`);
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
