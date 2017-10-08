export default (state = 0, action) => {
  switch (action.type) {
    case 'UPDATE_STATE':
      return action.value;
    default:
      return state
  }

};
