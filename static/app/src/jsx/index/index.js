class IndexApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            content: "nothing yet"
        };

        this.btnClick = this.btnClick.bind(this);
    }

    render() {
        return (
            <div className="main-container">
                <h1>Index</h1>
                <p>content: {this.state.content}</p>
                <span className="button" onClick={this.btnClick}>button</span>

                <a href="./login.html">登录</a>
                <a href="./register.html">注册</a>

            </div>
        )
    }

    btnClick() {
        this.setState({
            content: "btn clicked!"
        });
    }
}

ReactDOM.render(
    <IndexApp content="test content"/>,
    document.getElementById('root')
);