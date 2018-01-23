
var AddressForm = React.createClass({
   render: function() {
       return (
            <div>
                <div className="form-row">
                    <div className="form-group col-md-5">
                        <label for="address">Street address</label>
                        <input required type="text" className="form-control" id="address" placeholder="1234 Main St" />
                    </div>
                    <div className="form-group col-md-3">
                        <label for="city">City</label>
                        <input required type="text" className="form-control" id="city" />
                    </div>
                    <div className="form-group col-md-2">
                        <label for="state">State</label>
                        <select id="state" className="form-control">
                            <option value="AK">Alaska</option>
                            <option value="AL">Alabama</option>
                            <option value="AR">Arkansas</option>
                            <option value="AZ">Arizona</option>
                            <option value="CA">California</option>
                            <option value="CO">Colorado</option>
                            <option value="CT">Connecticut</option>
                            <option value="DC">District of Columbia</option>
                            <option value="DE">Delaware</option>
                            <option value="FL">Florida</option>
                            <option value="GA">Georgia</option>
                            <option value="HI">Hawaii</option>
                            <option value="IA">Iowa</option>
                            <option value="ID">Idaho</option>
                            <option value="IL">Illinois</option>
                            <option value="IN">Indiana</option>
                            <option value="KS">Kansas</option>
                            <option value="KY">Kentucky</option>
                            <option value="LA">Louisiana</option>
                            <option value="MA">Massachusetts</option>
                            <option value="MD">Maryland</option>
                            <option value="ME">Maine</option>
                            <option value="MI">Michigan</option>
                            <option value="MN">Minnesota</option>
                            <option value="MO">Missouri</option>
                            <option value="MS">Mississippi</option>
                            <option value="MT">Montana</option>
                            <option value="NC">North Carolina</option>
                            <option value="ND">North Dakota</option>
                            <option value="NE">Nebraska</option>
                            <option value="NH">New Hampshire</option>
                            <option value="NJ">New Jersey</option>
                            <option value="NM">New Mexico</option>
                            <option value="NV">Nevada</option>
                            <option value="NY">New York</option>
                            <option value="OH">Ohio</option>
                            <option value="OK">Oklahoma</option>
                            <option value="OR">Oregon</option>
                            <option value="PA">Pennsylvania</option>
                            <option value="PR">Puerto Rico</option>
                            <option value="RI">Rhode Island</option>
                            <option value="SC">South Carolina</option>
                            <option value="SD">South Dakota</option>
                            <option value="TN">Tennessee</option>
                            <option value="TX">Texas</option>
                            <option value="UT">Utah</option>
                            <option value="VA">Virginia</option>
                            <option value="VT">Vermont</option>
                            <option value="WA">Washington</option>
                            <option value="WI">Wisconsin</option>
                            <option value="WV">West Virginia</option>
                            <option value="WY">Wyoming</option>
                        </select>
                    </div>
                    <div className="form-group col-md-2">
                        <label for="zip">Zip</label>
                        <input required type="number" className="form-control" id="zip" />
                    </div>
                </div>
                
            </div>
        );
   } 
});

var OrderDropdown = React.createClass({
    render: function() {
        return (
            <div className="container dropdown">
                <p>Current {this.props.title}: </p>
                <form>
                    {this.props.children}
                    <input type='hidden' id='orderId' value={this.props.orderId} />
                    <button>Change</button>
                </form>
            </div>
        );
    }
});

var Order = React.createClass({
    
    getInitialState: function() {
        return {
            dropdownVisible: false,
            dropdownIndex: 0
        }
    },
    
    toggleDropdown: function(event, index) {
        event.preventDefault();
        
        if (this.state.dropdownVisible) {
            
            // clicking same button, close
            if (this.state.dropdownIndex === index) {
                this.setState({
                    dropdownVisible: false,
                    dropdownIndex: 0
                });
            }
            
            // clicking new button, dont close just change dialog
            else {
                this.setState({dropdownIndex: index});
            }
            
        }
        
        // otherwise show 
        else {
            this.setState({
                    dropdownVisible: true,
                    dropdownIndex: index
                });
        }
    },
    
    getDropdown: function() {
        // Not visible, dont render
        if (!this.state.dropdownVisible)
            return "";
        
        // Get the selected dropdown
        switch (this.state.dropdownIndex) {

            // Extend delivery date
            case 1:
                return (
                    <OrderDropdown title='Delivery date' orderId='1000'>
                        <div className="form-row">
                            <div className="form-group col-md-5">
                                <input required type="date" className="form-control" id="date" placeholder="9/21/2017" />
                            </div>
                        </div>
                    </OrderDropdown>
                );

            // Modify meal options
            case 2: 
                return (
                    <OrderDropdown title='Meal option' orderId='1000'>
                        <div className="form-group col-md-2">
                            <select id="mealOption" className="form-control">
                                <option value="br">Breakfast</option>
                                <option value="lu">Lunch</option>
                                <option value="di">Dinner</option>
                            </select>
                        </div>
                    </OrderDropdown>
                );
        
            // Modify delivery address
            case 3:
                return (
                    <OrderDropdown title='Address information' orderId='1000'>
                        <AddressForm />
                    </OrderDropdown>
                );
        
            default:
                return "";
        }
    },
    
    render: function() {
        return (
            <div className="container item">
                <div className="row">
                    <img className="col-sm-2" alt="meal-image" />
                    <div className="col-md-10 h-50">
                        <div className='space'>
                            <div className="row">
                                <h4>[ food type ]</h4>
                            </div>
                            <div className="row">
                                <p>[ food description ]</p>
                            </div>
                            <div className="row">
                                <span className='align-text-bottom align-bottom'>
                                    <span>Day ordered: [ date ]</span>, <span>Days remaining: [ number ]</span>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row justify-content-md-center">
                    <a onClick={(e) => this.toggleDropdown(e, 1)} className="col-md-3 btn btn-default" href="#" role="button">Extend delivery date</a>
                    <a onClick={(e) => this.toggleDropdown(e, 2)} className="col-md-3 btn btn-default" href="#" role="button">Modify meal options</a>
                    <a onClick={(e) => this.toggleDropdown(e, 3)} className="col-md-3 btn btn-default" href="#" role="button">Modify delivery address</a>
                </div>
                {this.getDropdown()}
            </div>
        );
    }
});

var OrderPage = React.createClass({
        
    render: function() {
        return (
            <div className='container'>
                <div className="page-header">
                    <h2>My orders</h2>
                </div>

                <p>Manage all your ongoing orders. You currently have <b>3</b> ongoing order/s.</p>
                <hr />

                <div className="container-fluid">
                    <Order />
                    <Order />
                    <Order />
                </div>
            </div>
        )
    }
});

React.render(<OrderPage />,
        document.getElementById('react-container'));
