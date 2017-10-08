import React, { Component } from "react";
import WsButton from "./WsButton";

class MainDivUi extends Component {

  componentDidMount() {
    this.getInitialState();
  }

  getInitialState() {
    console.log("fetching");
    fetch('http://localhost:9000/api/es/').then((response) => response.json().then((data) =>
      this.setState({value: data})
    ));
  }

  constructor(props) {
    super(props);
    this.setState = this.setState.bind(this);
    this.state = {value: "-"};
    this.websocket = this.setupSocket()
  }

  setupSocket = () => {
    var webSocket;
    webSocket = new WebSocket("ws://localhost:9000/api/es/ws");
    webSocket.onopen = () => {
      console.log("socket open and ready!");
    };

    webSocket.onmessage = (msg) => {
      console.log("Got a msg", msg);
      this.setState({value: msg.data});
    };

    webSocket.onclose = () => {
      console.log("ws closed :(")
    };

    return webSocket;

  };

  render() {
    return (
      <div className="container ">
        <div className="columns">
          <div className="column is-8-desktop is-offset-2-desktop is-12-mobile box">
            <div className="columns">
              <div className="column is-12 has-text-centered content">
                <span className="huge-number">{this.state.value}</span>
              </div>
            </div>
            <div className="columns">
              <div className="column is-12 has-text-centered is-mobile">
                <WsButton icon="fa-thumbs-down" onClick={() => this.websocket.send("-1")} type="is-primary"/>
                <WsButton icon="fa-bomb" onClick={() => this.websocket.send(-2147483648)} type="is-danger"/>
                <WsButton icon="fa-thumbs-up" onClick={() => this.websocket.send(1)} type="is-primary"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}


export default MainDivUi;
