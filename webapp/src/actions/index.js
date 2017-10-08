export const updateState = (state) => {
  return {
    type: 'UPDATE_STATE',
    value: state.value
  };
};