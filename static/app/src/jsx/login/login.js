class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.onLoginClick = this.onLoginClick.bind(this);
        this.onPasswordChange = this.onPasswordChange.bind(this);
        this.onUsernameChange = this.onUsernameChange.bind(this);
    }

    render() {
        return (
            <div className="main-container">
                <form className="commonForm">
                    <p>
                        <label htmlFor="username">用户名: </label>
                        <input id="username" type="text" onChange={this.onUsernameChange}/>
                    </p>

                    <p>
                        <label htmlFor="password">密码: </label>
                        <input id="password" type="password" onChange={this.onPasswordChange}/>
                    </p>

                    <p>
                        <input type="button" value="登录" onClick={this.onLoginClick}/>
                    </p>
                </form>
                <Loading show={this.state.inFlight}/>
                {this.state.responseMsg ? <p>{this.state.responseMsg}</p> : ''}
            </div>
        );
    }

    onUsernameChange(event) {
        console.log('username changed: ' + event.target.value);
        this.setState({
            username: event.target.value
        });
    }

    onPasswordChange(event) {
        console.log('password changed: ' + event.target.value);
        this.setState({
            password: event.target.value
        });
    }

    onLoginClick() {
        let self = this;
        self.setState({
            inFlight: true
        });
        console.log(this.state);
        $.post('/login', {username: this.state.username, password: this.state.password}, function(response, status) {
            self.setState({
                inFlight: false,
                responseMsg: response.info
            });
        }, 'json');
    }
}

ReactDOM.render(
    <Login/>,
    document.getElementById('root')
);