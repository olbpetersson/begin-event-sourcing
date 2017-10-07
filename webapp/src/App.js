import React, { Component } from 'react';
import './App.css';
import 'bulma/css/bulma.css';
import 'font-awesome/css/font-awesome.css'

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
                      <i class="fa fa-thumbs-down"></i>
                    </span>
                  </a>
                  <a className="button is-warning">+2</a>
                  <a className="button is-danger">
                    <span class="icon">
                      <i class="fa fa-bomb"></i>
                    </span>
                  </a>
                  <a className="button is-primary">
                    <span class="icon">
                      <i class="fa fa-thumbs-up"></i>
                    </span>
                  </a>
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
