import { shallowMount } from "@vue/test-utils";
import Counter from "@/components/Counter.vue";
import axios from 'axios';

jest.mock('axios', () => ({
  post: jest.fn(() => Promise.resolve({data: {id: 1 } })),
  get: jest.fn(() => Promise.resolve({ }))
}));

describe("Counter.vue", () => {
  it("counter is initialized with 0", () => {
    const wrapper = shallowMount(Counter);
    expect(wrapper.text()).toMatch("0");
  });

  it("counter is update when new count is set", () => {
    const wrapper = shallowMount(Counter);
    wrapper.setData({ count: 1 });
    expect(wrapper.text()).toMatch("1");
  });

  it("counters/increaseOrder is called onRaiseCount", () => {
    const wrapper = shallowMount(Counter);
    wrapper.find("div.count").trigger('click');
    expect(axios.post).toBeCalledWith("/counters/increaseOrder");
  });

  it("stream is created before component is mount", () => {
    const wrapper = shallowMount(Counter);
    expect(axios.post).toBeCalledWith("/streams");
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.streamId).toBe(1);
      done();
    })

  });
});
