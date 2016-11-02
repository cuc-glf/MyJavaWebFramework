class Chatroom extends React.Component {

    constructor(props) {
        super(props);
        /**
         * webSocket: 当前建立的长链接
         * connectState: 当前链接建立状态, 'disconnect', 'connecting', 'connected'
         * currentMessageId: 当前可用消息id
         * chatList: 聊天室中的发言列表, 包括本人的发言以及其他人的发言, 从客户端发出去的speech会立即添加进来, 但需要等待确认消息到达才能
         * 确定发言的确已经成功
         * userinfo: 当前用户信息
         * speech: 当前输入框内文字
         */
        this.state = {
            connectState: 'connecting',
            chatList: []
        };

        this.initChatroom = this.initChatroom.bind(this);
        this.onReconnect = this.onReconnect.bind(this);
        this.onSendClick = this.onSendClick.bind(this);
        this.onSpeechChange = this.onSpeechChange.bind(this);

        this.initChatroom();
    }

    render() {

        let renderedContent = null;
        if (this.state.connectState === 'disconnect') {
            renderedContent = (
                <div>
                    <span className="button" onClick={this.onReconnect}>重新链接</span>
                </div>
            );
        } else if (this.state.connectState === 'connecting' || typeof this.state.currentMessageId === 'undefined') {
            renderedContent = (
                <div>
                    <Loading/>
                </div>
            );
        } else if (this.state.connectState ==='connected') {
            renderedContent = (
                <div>
                    <p>当前用户: {this.state.userinfo.name}</p>
                    <div>
                        {this.state.chatList.map((item) => {
                            return (<ChatContent key={item.senderName + item.time} chatItem={item}/>);
                        })}
                    </div>
                    <div>
                        <input type="text" value={this.state.speech} onChange={this.onSpeechChange}/>
                        <span className="button" onClick={this.onSendClick}>发送</span>
                    </div>
                </div>
            );
        }

        return renderedContent;
    };

    initChatroom() {
        let self = this;
        $.get('/whoami')
        .done(function(response, status) {
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

            let url = 'ws://' + location.host + '/chat';
            let webSocket = new WebSocket(url);
            webSocket.onopen = function() {
                console.log('websocket opened');
                self.setState({
                    webSocket: webSocket,
                    connectState: 'connected'
                });
                webSocket.send(JSON.stringify({
                    messageType: 'getCurrentMessageId'
                }));
            };
            webSocket.onclose = function() {
                console.log('websocket closed');
                self.setState({
                    webSocket: null,
                    connectState: 'disconnect'
                });
            };
            webSocket.onerror = function(error) {
                console.log('websocket error');
                console.log(error);
            };
            webSocket.onmessage = function(event) {
                console.log('websocket onmessage');
                console.log(event);
                let messageStr = event.data;
                let messageObj = JSON.parse(messageStr);
                let type = messageObj.messageType;
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
            }
        }).fail(function() {
            self.setState({
                webSocket: null,
                connectState: 'disconnect'
            });
        });
    }

    onReconnect(event) {
        this.setState({
            connectState: 'connecting'
        });
        this.initChatroom();
    }

    onSpeechChange(event) {
        this.setState({
            speech: event.target.value
        });
    }

    onSendClick(event) {
        let webSocket = this.state.webSocket;
        let speech = this.state.speech;
        let currentTime = new Date().getTime();
        this.setState((prevState) => (
            {
                chatList: prevState.chatList.concat([{
                    messageType: 'PublicMessage',
                    content: speech,
                    time: currentTime,
                    senderName: prevState.userinfo.name
                }]),
                speech: '',
                currentMessageId: prevState.currentMessageId + 1
            }
        ));
        webSocket.send(JSON.stringify({
            messageId: this.state.currentMessageId + 1,
            messageType: 'sendToPublic',
            content: speech
    }));
    }

    static formatDate(date) {
        return date.getFullYear() + '-' + date.getMonth() + '-' + date.getDay() + ' ' + date.getHours() + ':'
            + date.getMinutes();
    }
}

ReactDOM.render(
    <Chatroom/>,
    document.getElementById('root')
);