import React, {Component} from 'react';
import {connect} from 'react-redux';
import {updateState} from '../actions/index'
import MainDivUi from "./MainDivUi";

// eslint-disable-next-line
class MainDivContainer extends Component {
  render() {
    return <MainDivUi updateState={updateState}/>
  }
}

function mapStateToProps(state) {
  return {
    value: state
  }
}

function mapDispatchToProps(dispatch) {
  return {
    updateState: (state) => dispatch(updateState(state))
  }
}


export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MainDivUi);
