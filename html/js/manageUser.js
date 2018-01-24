var UserRow = React.createClass({
    render: function(){
        return (
           <tr>
                    <td>{this.props.user.userid}</td>
                    <td>{this.props.user.name}</td>
                    <td>{this.props.user.email}</td>
                    <td>{this.props.user.status}</td>
                    <td>
                        <div className="btn-group">
                            <button type="button" className="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                 Manage User
                            </button>
                            <div className="dropdown-menu">
                                <a className="dropdown-item" href="#">Enable</a>
                                <a className="dropdown-item" href="#">Disable</a>
                                <a className="dropdown-item" href="#">Change Password</a>
                                    <div className="dropdown-divider"></div>
                                <a className="dropdown-item" href="#">Delete User</a>
                            </div>
                        </div>
                  </td>
         </tr>
            
        );
    }

});

 UserTable = React.createClass({

    eachUser: function( user, index ) 
     { 
        return ( 
            <UserRow key={index} user={user} /> 
        ); 
    }, 
 
  
    render: function (){
        return(
              <table className="table table-striped">
                <thead className="thead-dark">
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Status</th>
                    <th>Action</th>               
                </thead>
                    {this.props.users.map( this.eachUser )}
              </table>
        );
    },



});

       






