const client = jest.genMockFromModule('../client.js');

client.createStream = () => ({
  data: {
    id: 1
  }
});

export default client;
