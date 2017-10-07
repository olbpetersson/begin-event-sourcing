import React, { Component } from 'react';
import './App.css';
import 'bulma/css/bulma.css';

class App extends Component {
  render() {
    return (
      <section className="section">
        <div className="container ">
          <div className="columns">
            <div className="column is-8-desktop is-offset-2-desktop is-12-mobile box">
              <div className="columns">
                <div className="column is-12 has-text-centered content">
                  <span className="huge-number">1</span>
                </div>
              </div>
              <div className="columns">
                <div className="column is-12 has-text-centered is-mobile">
                  <a className="button is-primary">
                    <span class="icon">
                      <i class="fa fa-home"></i>
                    </span>
                  </a>
                  <a className="button is-primary">-1</a>
                  <a className="button is-primary">+2</a>
                  <a className="button is-primary">Crash</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    )
      ;
  }
}

export default App;
