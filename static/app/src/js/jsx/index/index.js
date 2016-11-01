"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var IndexApp = function (_React$Component) {
    _inherits(IndexApp, _React$Component);

    function IndexApp(props) {
        _classCallCheck(this, IndexApp);

        var _this = _possibleConstructorReturn(this, (IndexApp.__proto__ || Object.getPrototypeOf(IndexApp)).call(this, props));

        _this.state = {
            content: "nothing yet"
        };

        _this.btnClick = _this.btnClick.bind(_this);
        return _this;
    }

    _createClass(IndexApp, [{
        key: "render",
        value: function render() {
            return React.createElement(
                "div",
                { className: "mainContainer" },
                React.createElement(
                    "h1",
                    null,
                    "Index"
                ),
                React.createElement(
                    "p",
                    null,
                    "content: ",
                    this.state.content
                ),
                React.createElement(
                    "span",
                    { className: "button", onClick: this.btnClick },
                    "button"
                ),
                React.createElement(
                    "a",
                    { href: "./login.html" },
                    "\u767B\u5F55"
                ),
                React.createElement(
                    "a",
                    { href: "./register.html" },
                    "\u6CE8\u518C"
                )
            );
        }
    }, {
        key: "btnClick",
        value: function btnClick() {
            this.setState({
                content: "btn clicked!"
            });
        }
    }]);

    return IndexApp;
}(React.Component);

ReactDOM.render(React.createElement(IndexApp, { content: "test content" }), document.getElementById('root'));
