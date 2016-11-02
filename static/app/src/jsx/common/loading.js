class Loading extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            dots: ".",
            show: typeof props.show === 'undefined' ? true : props.show
        }
    }

    componentDidMount() {
        this.timerID = setInterval(
            () => this.tick(),
            300
        );
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    tick() {
        this.setState(() => {
            return {
                dots: this.state.dots.length == 6 ? "." : "." + this.state.dots
            };
        });
    }

    render() {
        let element = (<span>
                {this.state.dots}
            </span>);
        return this.state.show ? element : null;
    }

}