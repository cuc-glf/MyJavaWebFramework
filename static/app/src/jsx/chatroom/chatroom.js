class Chatroom extends React.Component {

    constructor(props) {
        super(props);
        /**
         * connectState: 当前链接建立状态, 'disconnect', 'connecting', 'connected'
         * chatList: 聊天室中的发言列表
         * userinfo: 当前用户信息
         */
        this.state = {
            connectState: 'connecting',
            chatList: []
        };
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
        } else if (this.state.connectState === 'connecting') {
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
                            return <p>{item.senderName}({item.time}): {item.content}</p>
                        })}
                    </div>
                    <div>
                        <input type="text" onChange={this.onSpeechChange}/>
                        <p className="button" onClick={this.onSendClick}>发送</p>
                    </div>
                </div>
            );
        }

        return renderedContent;
    };

    initChatroom() {
        let self = this;
        $.post('/whoami', function(response, status) {
            if (response.code != 0) {
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
                    connectState: 'connected'
                });
            };
            webSocket.onclose = function() {
                console.log('websocket closed');
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
                if (type === 'SendToPublic') {
                    //public String content;      // 发言内容
                    //public Date time;           // 发言时间
                    //public String senderName;   // 发言用户名
                    self.setState({
                        chatList: self.state.chatList.concat([messageObj])
                    });
                }
            }
        });
    }

    onReconnect(event) {

    }

    onSpeechChange(event) {

    }

    onSendClick(event) {

    }
}