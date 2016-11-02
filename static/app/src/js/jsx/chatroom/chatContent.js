'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var ChatContent = function (_React$Component) {
    _inherits(ChatContent, _React$Component);

    function ChatContent(props) {
        _classCallCheck(this, ChatContent);

        var _this = _possibleConstructorReturn(this, (ChatContent.__proto__ || Object.getPrototypeOf(ChatContent)).call(this, props));

        _this.state = {};
        _this.onRetryClick = _this.onRetryClick.bind(_this);

        return _this;
    }

    _createClass(ChatContent, [{
        key: 'render',
        value: function render() {
            var item = this.props.chatItem;
            var sendState = null;
            if (typeof item === 'undefined') {
                return null;
            }
            if (item.sendState === 'sending') {
                sendState = React.createElement(
                    'span',
                    null,
                    '[sending]'
                );
            } else if (item.sendState === 'timeout') {
                sendState = React.createElement(
                    'span',
                    null,
                    '[timeout]',
                    React.createElement(
                        'span',
                        { className: 'button', onClick: this.onRetryClick },
                        '\u91CD\u8BD5'
                    )
                );
            } else if (item.sendState === 'succeed') {
                sendState = React.createElement(
                    'span',
                    null,
                    '[ok]'
                );
            }
            return React.createElement(
                'p',
                null,
                item.senderName,
                '(',
                Chatroom.formatDate(new Date(item.time)),
                '):',
                item.content,
                sendState
            );
        }
    }, {
        key: 'onRetryClick',
        value: function onRetryClick() {
            this.props.onRetryClick();
        }
    }]);

    return ChatContent;
}(React.Component);
