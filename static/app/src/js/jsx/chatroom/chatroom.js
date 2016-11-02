'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Chatroom = function (_React$Component) {
    _inherits(Chatroom, _React$Component);

    function Chatroom(props) {
        _classCallCheck(this, Chatroom);

        /**
         * webSocket: 当前建立的长链接
         * connectState: 当前链接建立状态, 'disconnect', 'connecting', 'connected'
         * currentMessageId: 当前可用消息id
         * chatList: 聊天室中的发言列表, 包括本人的发言以及其他人的发言, 从客户端发出去的speech会立即添加进来, 但需要等待确认消息到达才能
         * 确定发言的确已经成功
         * userinfo: 当前用户信息
         * speech: 当前输入框内文字
         */
        var _this = _possibleConstructorReturn(this, (Chatroom.__proto__ || Object.getPrototypeOf(Chatroom)).call(this, props));

        _this.state = {
            connectState: 'connecting',
            chatList: []
        };

        _this.initChatroom = _this.initChatroom.bind(_this);
        _this.onReconnect = _this.onReconnect.bind(_this);
        _this.onSendClick = _this.onSendClick.bind(_this);
        _this.onSpeechChange = _this.onSpeechChange.bind(_this);

        _this.initChatroom();
        return _this;
    }

    _createClass(Chatroom, [{
        key: 'render',
        value: function render() {

            var renderedContent = null;
            if (this.state.connectState === 'disconnect') {
                renderedContent = React.createElement(
                    'div',
                    null,
                    React.createElement(
                        'span',
                        { className: 'button', onClick: this.onReconnect },
                        '\u91CD\u65B0\u94FE\u63A5'
                    )
                );
            } else if (this.state.connectState === 'connecting' || typeof this.state.currentMessageId === 'undefined') {
                renderedContent = React.createElement(
                    'div',
                    null,
                    React.createElement(Loading, null)
                );
            } else if (this.state.connectState === 'connected') {
                renderedContent = React.createElement(
                    'div',
                    null,
                    React.createElement(
                        'p',
                        null,
                        '\u5F53\u524D\u7528\u6237: ',
                        this.state.userinfo.name
                    ),
                    React.createElement(
                        'div',
                        null,
                        this.state.chatList.map(function (item) {
                            return React.createElement(ChatContent, { key: item.senderName + item.time, chatItem: item });
                        })
                    ),
                    React.createElement(
                        'div',
                        null,
                        React.createElement('input', { type: 'text', value: this.state.speech, onChange: this.onSpeechChange }),
                        React.createElement(
                            'span',
                            { className: 'button', onClick: this.onSendClick },
                            '\u53D1\u9001'
                        )
                    )
                );
            }

            return renderedContent;
        }
    }, {
        key: 'initChatroom',
        value: function initChatroom() {
            var self = this;
            $.get('/whoami').done(function (response, status) {
                if (response.code != 0) {
                    self.setState({
                        connectState: 'disconnect',
                        webSocket: null
                    });
                    return;
                }

                self.setState({
                    userinfo: response.user
                });

                var url = 'ws://' + location.host + '/chat';
                var webSocket = new WebSocket(url);
                webSocket.onopen = function () {
                    console.log('websocket opened');
                    self.setState({
                        webSocket: webSocket,
                        connectState: 'connected'
                    });
                    webSocket.send(JSON.stringify({
                        messageType: 'getCurrentMessageId'
                    }));
                };
                webSocket.onclose = function () {
                    console.log('websocket closed');
                    self.setState({
                        webSocket: null,
                        connectState: 'disconnect'
                    });
                };
                webSocket.onerror = function (error) {
                    console.log('websocket error');
                    console.log(error);
                };
                webSocket.onmessage = function (event) {
                    console.log('websocket onmessage');
                    console.log(event);
                    var messageStr = event.data;
                    var messageObj = JSON.parse(messageStr);
                    var type = messageObj.messageType;
                    if (type === 'PublicMessage') {
                        //public String content;      // 发言内容
                        //public Date time;           // 发言时间
                        //public String senderName;   // 发言用户名
                        self.setState({
                            chatList: self.state.chatList.concat([messageObj])
                        });
                    } else if (type === 'CurrentMessageId') {
                        //public int currentMessageId;
                        self.setState({
                            currentMessageId: messageObj.currentMessageId
                        });
                    }
                };
            }).fail(function () {
                self.setState({
                    webSocket: null,
                    connectState: 'disconnect'
                });
            });
        }
    }, {
        key: 'onReconnect',
        value: function onReconnect(event) {
            this.setState({
                connectState: 'connecting'
            });
            this.initChatroom();
        }
    }, {
        key: 'onSpeechChange',
        value: function onSpeechChange(event) {
            this.setState({
                speech: event.target.value
            });
        }
    }, {
        key: 'onSendClick',
        value: function onSendClick(event) {
            var webSocket = this.state.webSocket;
            var speech = this.state.speech;
            var currentTime = new Date().getTime();
            this.setState(function (prevState) {
                return {
                    chatList: prevState.chatList.concat([{
                        messageType: 'PublicMessage',
                        content: speech,
                        time: currentTime,
                        senderName: prevState.userinfo.name
                    }]),
                    speech: '',
                    currentMessageId: prevState.currentMessageId + 1
                };
            });
            webSocket.send(JSON.stringify({
                messageId: this.state.currentMessageId + 1,
                messageType: 'sendToPublic',
                content: speech
            }));
        }
    }], [{
        key: 'formatDate',
        value: function formatDate(date) {
            return date.getFullYear() + '-' + date.getMonth() + '-' + date.getDay() + ' ' + date.getHours() + ':' + date.getMinutes();
        }
    }]);

    return Chatroom;
}(React.Component);

ReactDOM.render(React.createElement(Chatroom, null), document.getElementById('root'));
