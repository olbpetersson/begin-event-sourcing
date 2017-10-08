import React, {Component} from 'react';
import {connect} from 'react-redux';
import Header from './Header';
import {loginUser} from '../actions/login'

class MainDiv extends Component {
  render() {
    return <MainDivUi />
  }
}

function mapStateToProps(state) {
  return {
    value: state
  }
}

function mapDispatchToProps(dispatch) {
  return {
    loginUser: (user) => dispatch(loginUser(user))
  }
}


export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Header);
