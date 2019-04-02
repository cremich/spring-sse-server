export default {
  methods: {
    addEventListener() {
      let self = this;
      this.eventSource = new EventSource("/streams/" + this.streamId + "/data");
      this.eventSource.addEventListener(
        "count",
        function(e) {
          self.count = e.data;
        },
        false
      );

      component.eventSource.onerror = function(e) {
        console.log(e);
      };
    }
  }
};
