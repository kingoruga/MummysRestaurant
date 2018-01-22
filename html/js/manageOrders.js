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
    render: function()
    {
        return (
            <table className="table table-striped">
                <thead className="thead-dark">
                    <th>Order ID</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Price</th>
                    <th>Payment</th>
                </thead>
                {this.props.orders.map( this.eachOrder )}
            </table>
        );
    }
});