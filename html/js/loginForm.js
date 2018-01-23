LoginForm = React.createClass({
    getInitialState: function()
    {
        return {
          email: "",
          password: ""
        };
    },
    handleChange: function(event)
    {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        var partialState = {};
        partialState[name] = value;
        this.setState(partialState);
    },
    handleSubmit: function(event)
    {
        event.preventDefault();
        console.log( "Submit" );
        console.log( this.state.email );
        console.log( this.state.password );
    },
    render: function()
    {
        return (
            <div className="d-flex justify-content-center">
                <form onSubmit={this.handleSubmit.bind(this)} >
                    <div className="form-group">
                        <label> Email address</label>
                        <input 
                            type="email" className="form-control" name="email"
                            aria-describedby="emailHelp" placeholder="Enter email" 
                            value={this.state.email}
                            onChange={this.handleChange.bind(this)} />
                    </div>
                    <div className="form-group">
                        <label>Password</label>
                        <input type="password" className="form-control"
                        placeholder="Password" name="password"
                        value={this.state.password}
                        onChange={this.handleChange.bind( this )} />
                    </div>
                    <button type="submit" className="btn btn-primary">Submit</button>
                </form>
            </div>        
        );
    }
}
);