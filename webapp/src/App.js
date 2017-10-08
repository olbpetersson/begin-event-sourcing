import React, { Component } from 'react';
import './App.css';
import 'bulma/css/bulma.css';
import 'font-awesome/css/font-awesome.css'
import MainDivUi from "./components/MainDivUi";

class App extends Component {
  render() {
    return (
      <section className="section">
        <MainDivUi/>
      </section>
    )
      ;
  }
}

export default App;
