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
    }

    render() {

        let renderedContent = null;
        if (this.state.connectionEstablished) {
            renderedContent = (
                <div></div>
            );
        } else {
            renderedContent = (
                <div></div>
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
}