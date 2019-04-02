import axios from "axios/index";

const resourcePath = "/counters";

export default {
  async increaseCount() {
    return axios.post(resourcePath + "/increaseOrder");
  }
};
