class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};

        this.onUsernameChange = this.onUsernameChange.bind(this);
        this.onPasswordChange = this.onPasswordChange.bind(this);
        this.onPhoneNumberChange = this.onPhoneNumberChange.bind(this);
        this.onRegisterClick = this.onRegisterClick.bind(this);
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
                        <label htmlFor="phoneNumber">手机号: </label>
                        <input id="phoneNumber" type="number" onChange={this.onPhoneNumberChange}/>
                    </p>

                    <p>
                        <input type="button" value="注册" onClick={this.onRegisterClick}/>
                    </p>
                </form>
                <Loading show={this.state.inFlight}/>
                {this.state.responseMsg ? <p>{this.state.responseMsg}</p> : ''}
            </div>
        );
    }

    onUsernameChange(event) {
        this.setState({username: event.target.value});
    }

    onPasswordChange(event) {
        this.setState({password: event.target.value});
    }

    onPhoneNumberChange(event) {
        this.setState({phoneNumber: event.target.value});
    }

    onRegisterClick() {
        let self = this;
        this.setState({inFlight: true});
        $.post('/register', {
            username: this.state.username,
            password: this.state.password,
            phone: this.state.phoneNumber
        }, function(response, status) {
            self.setState({
                inFlight: false,
                responseMsg: response.info
            });
        });
    }
}

ReactDOM.render(
    <Register/>,
    document.getElementById('root')
);