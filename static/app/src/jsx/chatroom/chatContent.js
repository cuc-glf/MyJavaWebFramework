class ChatContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {

        };
        this.onRetryClick = this.onRetryClick.bind(this);

    }

    render() {
        let item = this.props.chatItem;
        let sendState = null;
        if (typeof item === 'undefined') {
            return null;
        }
        if (item.sendState === 'sending') {
            sendState = (<span>[sending]</span>);
        } else if (item.sendState === 'timeout') {
            sendState = (<span>[timeout]<span className="button" onClick={this.onRetryClick}>重试</span></span>);
        } else if (item.sendState === 'succeed') {
            sendState = (<span>[ok]</span>);
        }
        return (
            <p>
                {item.senderName}
                ({Chatroom.formatDate(new Date(item.time))}):
                {item.content}
                {sendState}
            </p>
        );
    }

    onRetryClick() {
        this.props.onRetryClick();
    }
}