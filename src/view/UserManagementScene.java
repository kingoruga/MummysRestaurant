
	package view;

	import java.util.ArrayList;
	import java.util.List;

	import com.syntel.*;

	public class UserManagementScene extends Scene {
		
		private String email;
		private String password;
		private UserController controller;
		
		@Override
		public Scene transitionNext() {
			
			controller = new UserController();
					
			switch(selectedChoice) {
			
				case "Delete User":
					controller.updateModelDeleteUser(email);
					break;
					
				case "Ban User":
					controller.updateModelDisableUser(email);
					break;
					
				case "Unban User":
					controller.updateModelEnableUser(email);
					break;
					
				case "Change User Password":
					controller.updateModelChangePassword(email,password);
					break;
					
				case "Back":
					return new HomeScene();			
					
			}		
					
			return new UserManagementScene();
			
		}
		
		
		@Override
		public void process() {
			List<String> choices = new ArrayList<>();
			
			choices.add("Delete User");
			choices.add("Ban User");
			choices.add("Unban User");
			choices.add("Change User Password");
			choices.add("Back");
			
			do {
				for(int i=0;i<choices.size();i++) {
					System.out.println("(" + (i) + ")" +" "+ choices.get(i));
				}
				
				selectedChoice = matchInputWithChoice(scanner.nextLine(), choices);
				
			}while(selectedChoice == null);
			
			if(!selectedChoice.equals("Back")){
				System.out.print("Enter User Email Address: ");				
				email = scanner.nextLine();	
				if(selectedChoice.equals("Change User Password")){
					System.out.print("Enter New User Password: ");
					password = scanner.nextLine();
					
				}
			}		
			
			requestTransition = true;
			
			
		}

	}


