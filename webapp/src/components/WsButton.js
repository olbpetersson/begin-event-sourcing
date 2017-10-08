import React, { Component} from 'react';

class WsButton extends Component {

  render() {
    return (
      <a className={"button " + this.props.type} onClick={this.props.onClick}>
                        <span className="icon">
                          <i className={"fa " + this.props.icon}>{this.props.label}</i>
                        </span>
      </a>
    )
  }
}

WsButton.defaultProps = {
  label: "",
  icon: ""
};

export default WsButton;
