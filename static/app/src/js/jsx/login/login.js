"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Login = function (_React$Component) {
    _inherits(Login, _React$Component);

    function Login(props) {
        _classCallCheck(this, Login);

        var _this = _possibleConstructorReturn(this, (Login.__proto__ || Object.getPrototypeOf(Login)).call(this, props));

        _this.state = {
            inFlight: false
        };
        _this.onLoginClick = _this.onLoginClick.bind(_this);
        _this.onPasswordChange = _this.onPasswordChange.bind(_this);
        _this.onUsernameChange = _this.onUsernameChange.bind(_this);
        return _this;
    }

    _createClass(Login, [{
        key: "render",
        value: function render() {
            return React.createElement(
                "div",
                { className: "main-container" },
                React.createElement(
                    "form",
                    { className: "commonForm" },
                    React.createElement(
                        "p",
                        null,
                        React.createElement(
                            "label",
                            { htmlFor: "username" },
                            "\u7528\u6237\u540D: "
                        ),
                        React.createElement("input", { id: "username", type: "text", onChange: this.onUsernameChange })
                    ),
                    React.createElement(
                        "p",
                        null,
                        React.createElement(
                            "label",
                            { htmlFor: "password" },
                            "\u5BC6\u7801: "
                        ),
                        React.createElement("input", { id: "password", type: "password", onChange: this.onPasswordChange })
                    ),
                    React.createElement(
                        "p",
                        null,
                        React.createElement("input", { type: "button", value: "\u767B\u5F55", onClick: this.onLoginClick })
                    )
                ),
                React.createElement(Loading, { show: this.state.inFlight }),
                this.state.responseMsg ? React.createElement(
                    "p",
                    null,
                    this.state.responseMsg
                ) : ''
            );
        }
    }, {
        key: "onUsernameChange",
        value: function onUsernameChange(event) {
            console.log('username changed: ' + event.target.value);
            this.setState({
                username: event.target.value
            });
        }
    }, {
        key: "onPasswordChange",
        value: function onPasswordChange(event) {
            console.log('password changed: ' + event.target.value);
            this.setState({
                password: event.target.value
            });
        }
    }, {
        key: "onLoginClick",
        value: function onLoginClick() {
            var self = this;
            self.setState({
                inFlight: true
            });
            console.log(this.state);
            $.post('/login', { username: this.state.username, password: this.state.password }, function (response, status) {
                self.setState({
                    inFlight: false,
                    responseMsg: response.info
                });
            }, 'json');
        }
    }]);

    return Login;
}(React.Component);

ReactDOM.render(React.createElement(Login, null), document.getElementById('root'));
