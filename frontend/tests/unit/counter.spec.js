import { shallowMount } from "@vue/test-utils";
import Counter from "@/components/Counter.vue";

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
});
