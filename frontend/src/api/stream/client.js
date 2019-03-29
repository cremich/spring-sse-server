import axios from "axios/index";

const resourcePath = "/streams";

export default {
  async createStream() {
    return await axios.post(resourcePath);
  }
};
