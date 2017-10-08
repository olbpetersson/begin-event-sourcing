import React, { Component } from 'react';
import './App.css';
import 'bulma/css/bulma.css';
import 'font-awesome/css/font-awesome.css'
import MainDivContainer from "./components/MainDivContainer";

class App extends Component {
  render() {
    return (
      <section className="section">
        <MainDivContainer />
      </section>
    )
      ;
  }
}

export default App;
