//renders out a table row for the order given as a prop
OrderRow = React.createClass({
   render: function()
   {
       return (
            <tr>
                <td>{this.props.order.orderid}</td>
                <td>{this.props.order.email}</td>
                <td>{this.props.order.name}</td>
                <td>{this.props.order.address}</td>
                <td>{this.props.order.price}</td>
                <td>{this.props.order.payment}</td>
            </tr>    
        );
   }
});

//renders out a table and uses OrderRow for rows for the orders given as a prop
OrderTable = React.createClass({
    eachOrder: function( order, index )
    {
        return (
            <OrderRow key={index} order={order} />
        );
    },
    handleClickSort: function( event )
    {
        event.preventDefault();
        //gets the data-column attribute from the html
        var columnSortBy = event.target.dataset.column;
        //TODO: need to either sort the data client side or send a new ajax request
        //TODO: need to propagate this event up to parent component that has state
        //TODO: remove the following when implemented
        console.log( columnSortBy );
        alert( "CLICKED" );
    },
    renderTableHeader: function()
    {
        return (
            <thead className="thead-dark">
                <th>Order ID 
                    &nbsp;<a href='#' data-column="orderid"
                        onClick={this.handleClickSort.bind( this )}>&#8595;</a>
                </th>
                <th>Email
                    &nbsp;<a href='#' data-column="email"
                        onClick={this.handleClickSort.bind( this )}>&#8595;</a>
                </th>
                <th>Name 
                    &nbsp;<a href='#' data-column="name"
                        onClick={this.handleClickSort.bind( this )}>&#8595;</a>
                </th>
                <th>Address 
                    &nbsp;<a href='#' data-column="address"
                        onClick={this.handleClickSort.bind( this )}>&#8595;</a>
                </th>
                <th>Price 
                    &nbsp;<a href='#' data-column="price"
                        onClick={this.handleClickSort.bind( this )}>&#8595;</a>
                </th>
                <th>Payment 
                    &nbsp;<a href='#' data-column="payment"
                        onClick={this.handleClickSort.bind( this )}>&#8595;</a>
                </th>
            </thead> 
        );
    },
    render: function()
    {
        return (
            <table className="table table-striped">
                {this.renderTableHeader()}
                {this.props.orders.map( this.eachOrder )}
            </table>
        );
    }
});