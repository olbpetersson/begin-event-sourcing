import React, { Component } from "react";
import WsButton from "./WsButton";
import { API_URL } from "../index";

class MainDivUi extends Component {

  componentDidMount() {
    this.fetchState();
  }

  fetchState() {
    fetch("http://" +API_URL).then((response) => response.json().then((data) =>
      this.props.updateState({value: data})
  ));
  }

  constructor(props) {
    super(props);
    this.setState = this.setState.bind(this);
    this.fetchState = this.fetchState.bind(this);
    //this.state = {value: "-"};
    this.websocket = this.setupSocket()
  }

  setupSocket = () => {
    var webSocket;
    webSocket = new WebSocket("ws://" +API_URL + "ws");
    webSocket.onopen = () => {
      console.log("socket open and ready!");
    };

    webSocket.onmessage = (msg) => {
      var action = {value: msg.data};
      this.props.updateState(action);
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
                <span className="huge-number">{this.props.value}</span>
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
