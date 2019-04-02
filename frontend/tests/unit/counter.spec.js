import { createLocalVue, shallowMount } from "@vue/test-utils";
import Counter from "@/components/Counter.vue";
import counterClient from "../../src/api/counter/client";
import flushPromises from "flush-promises";

jest.mock("../../src/api/counter/client");
jest.mock("../../src/api/stream/client");

let mockedMixin = {
  methods: {
    addEventListener() {}
  }
};

describe("Counter.vue", () => {
  it("counter is initialized with 0", () => {
    const wrapper = shallowMount(Counter, { mixins: [mockedMixin] });
    expect(wrapper.text()).toMatch("0");
  });

  it("counter is update when new count is set", () => {
    const wrapper = shallowMount(Counter, { mixins: [mockedMixin] });
    wrapper.setData({ count: 1 });
    expect(wrapper.text()).toMatch("1");
  });

  it("counters/increaseOrder is called onRaiseCount", () => {
    const wrapper = shallowMount(Counter, { mixins: [mockedMixin] });
    wrapper.find("div.count").trigger("click");
    expect(counterClient.increaseCount).toBeCalled();
  });

  it("stream is created before component is mount", async () => {
    const localVue = createLocalVue();
    localVue.mixin(mockedMixin);
    const wrapper = shallowMount(Counter, { mixins: [mockedMixin] });
    await flushPromises();
    expect(wrapper.vm.streamId).toBe(1);
  });
});
