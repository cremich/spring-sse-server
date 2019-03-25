<template>
  <div class="count">
    <h1>{{ count }}</h1>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Counter",
  data() {
    return {
      count: 0,
      streamId: 0
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
      return axios
        .post(`/streams`)
        .then(response => {
          this.streamId = response.data.id;
        })
        .catch(e => {
          console.log(e);
        });
    },
    addEventListener: function() {
      let self = this;
      let source = new EventSource("/streams/" + this.streamId + "/data");
      source.addEventListener(
        "count",
        function(e) {
          self.count = e.data;
        },
        false
      );
      source.onerror = function() {
        self.streamId = 0;
      };
    }
  },
  watch: {
    // whenever question changes, this function will run
    streamId: function(newStreamId) {
      if (newStreamId === 0) {
        this.registerEventSource();
      }
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.count {
  display: flex;
  height: 100%;
  align-items: center;
  justify-content: center;
}
h1 {
  margin: 0;
  font-size: 13rem;
  color: #eceff1;
}
label {
  font-size: 1.5rem;
}
</style>
