var DropdownMenu = React.createClass({
    render: function(){
        return (
            <div class="btn-group">
              <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Manage User
              </button>
               <div class="dropdown-menu">
                    <a class="dropdown-item" href="#">Enable</a>
                    <a class="dropdown-item" href="#">Disable</a>
                    <a class="dropdown-item" href="#">Change Password</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#">Delete User</a>
                </div>
            </div>

        );
    }

});

React.render(<DropdownMenu>Drop down</DropdownMenu>,document.getElementById('dropdown-menu'));


