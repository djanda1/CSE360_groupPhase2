package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.io.*;

public class UserManagementApp extends Application {
	private Map<String, User> users = new HashMap<>();
	private User currentUser;
	public String oneTimePassword, oneTimeStudent, oneTimeInstructor, oneTimeAdmin, oneTimeStudentIns, oneTimeReset;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("User Management Application");
		showLoginPage(primaryStage);
	}

	private void showAdminPage(Stage stage, String user) {		//show admin main home page
		save();
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		Button resetUser = new Button("Reset Users Account");
		TextField resetUserInput = new TextField();
		resetUserInput.setPromptText("Enter username for user you want to reset");
		
		Button listUsers = new Button("List Users");
		Button deleteUsers = new Button("Delete a user");
		TextField deleteAccountInput = new TextField();
		Button generatePasswordStudent = new Button("Generate invite code for student");
		Button generatePasswordInstructor = new Button("Generate invite code password for instructor");
		Button generatePasswordAdmin = new Button("Generate invite code for admin");
		Button generatePasswordStuIns = new Button("Generate invite code for student and instructor");
		Button logout = new Button("Log Out");
		Button articles = new Button("Articles");
		
		//button actions
		resetUser.setOnAction(e -> {
			String name = resetUserInput.getText();
			User resetUsername = users.get(name);
			if(resetUsername != null)
			{
				oneTimeReset = generateRandomPassword(8);
				System.out.print("Reset one-time password is: " + oneTimeReset + "\nThis password expires 12/31/2024");
				resetUser(stage, name);
				save();
			}
			else
				showAlert("Error", "This username does not exist, please enter a valid account username");
		});
		logout.setOnAction(e -> showLoginPage(stage));		//handles logout
		generatePasswordStudent.setOnAction(e -> {		//handle generating password
			oneTimeStudent = generateRandomPassword(8);
			System.out.print("Student's invite code: " + oneTimeStudent + "\nThis code expires 12/31/2024\n");
		});
		generatePasswordInstructor.setOnAction(e -> {
			oneTimeInstructor = generateRandomPassword(8);
			System.out.print("Instructor's invite code: " + oneTimeInstructor + "\nThis code expires 12/31/2024\n");
		});
		generatePasswordAdmin.setOnAction(e-> {
			oneTimeAdmin = generateRandomPassword(8);
			System.out.print("Admin's invite code: " + oneTimeAdmin + "\nThis code expires 12/31/2024\n");
		});
		generatePasswordStuIns.setOnAction(e-> {
			oneTimeStudentIns = generateRandomPassword(8);
			System.out.print("Invite code for both student and instructor role: " + oneTimeInstructor + "\nThis code expires 12/31/2024\n");
		});
		articles.setOnAction(e-> articleHomePage(stage));
		
		deleteAccountInput.setPromptText("Enter the username of the account desired to be deleted");
		
		deleteUsers.setOnAction(e -> {
			
			String username = deleteAccountInput.getText();
			User deleteUser = users.get(username);
			if (deleteUser != null) {
				confirmDelete(stage,deleteAccountInput.getText(),username);	//Show the confirmation page
			} else {
				showAlert("Error", "This username does not exist, please enter a valid account username");
			}
		});
		
		layout.getChildren().addAll(listUsers, deleteAccountInput, deleteUsers, resetUserInput, resetUser, generatePasswordStudent, generatePasswordInstructor, generatePasswordAdmin, generatePasswordStuIns,articles,logout);
		Scene scene = new Scene(layout, 500, 500);
		stage.setScene(scene);
		listUsers.setOnAction(e -> listUsers(stage));
	}
	
	private void articleHomePage(Stage stage)	{
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		Label action = new Label("What action would you like to take?");
		
		//buttons for the article page
		Button listArticles = new Button("List Articles");
		Button createArticle = new Button("Create");
		Button deleteArticle = new Button("Delete");
		Button viewArticle = new Button("View Article");
		Button goBack = new Button("Go Back");
		
		//text fields for buttons
		TextField createArticleInput = new TextField();
		TextField deleteArticleInput = new TextField();
		TextField viewArticleInput = new TextField();
		
		//Set prompts for text fields
		createArticleInput.setPromptText("Enter the title of the article you wish to create");
		deleteArticleInput.setPromptText("Enter the title of the article you wish to delete");
		viewArticleInput.setPromptText("Enter the title of the article you wish to view");

		//button actions
		if(currentUser.getRoles().get(0).equals("Admin"))
			goBack.setOnAction(e ->showAdminPage(stage, "admin"));
		else if(currentUser.getRoles().get(0).equals("Instructor"))
			goBack.setOnAction(e ->showHomePageInstructor(stage, "Instructor"));
		viewArticle.setOnAction(e -> {
			String name = viewArticle.getText();
			if(!name.isEmpty())
			{
				articleWindow(stage, name);
			}
			else
				showAlert("Error", "Please enter the email you would like to reset");
		});
		
		layout.getChildren().addAll(action,listArticles,createArticleInput,createArticle,deleteArticleInput,deleteArticle,viewArticleInput,viewArticle,goBack);
		Scene scene = new Scene(layout, 500, 500);
		stage.setScene(scene);
		stage.show();
	}
	
	private void articleWindow(Stage stage, String ArticleName)	{
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		//text field for article text
		TextField article = new TextField();
		article.setPromptText("Blah blah blah blah");
		//buttons
		Button goBack = new Button("Go Back");
		Button updateArticle = new Button("Update Article");
		//button actions
		goBack.setOnAction(e -> articleHomePage(stage));
		layout.getChildren().addAll(article, goBack, updateArticle);
		Scene scene = new Scene(layout, 500, 500);
		stage.setScene(scene);
		stage.show();
	}
	
	private void listUsers(Stage stage) {
		ObservableList<User> userList = FXCollections.observableArrayList(users.values());
		ListView<User> listview = new ListView<>(userList);
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e ->showAdminPage(stage, "Admin"));
		layout.getChildren().addAll(listview, goBack);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
		stage.show();
		
	}
	
	private void confirmDelete(Stage stage, String name, String admin) {			//confirmation of deletion page
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		Label usernameLabel = new Label("Are you sure you would like to\ndelete this account?");
		//buttons
		Button confirmDelete = new Button("Delete");
		Button goBack = new Button("Go Back");
		//button actions
		goBack.setOnAction(e ->showAdminPage(stage, "Admin"));
		confirmDelete.setOnAction(e -> {				//handles delete account button
			if (!name.isEmpty()) {
				deleteAccount(stage, name, admin);
			} else {
				showAlert("Error", "Please enter an account username");
			}
		});
		
		layout.getChildren().addAll(usernameLabel, confirmDelete,goBack);
		
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
		stage.show();
		
	}
	
	private void deleteAccount(Stage stage, String name, String admin) {			//delete user from list method
		User user = users.get(name);
		if (user != null) {
			users.remove(name);
			save();
			showAlert("Success", "Account has been deleted");
		} else {
			showAlert("Error", "This email does not exist, please enter a valid account email");
		}
		showAdminPage(stage,admin);
	}
	private void resetUser(Stage stage, String email)		//resets users account
	{
		User user = users.get(email);
		if(user != null)
		{
			user.setReset(true);
			showAlert("Success", "Account has been reset");
		}
		else
		{
			showAlert("Error", "Username does not exist");
		}
	}

	private void showLoginPage(Stage stage) {		//first page, will be login page with buttons to use one time password
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 0, 0);
		TextField usernameInput = new TextField();
		GridPane.setConstraints(usernameInput, 1, 0);

		Label passwordLabel = new Label("Password:");
		GridPane.setConstraints(passwordLabel, 0, 1);
		PasswordField passwordInput = new PasswordField();
		GridPane.setConstraints(passwordInput, 1, 1);

		Button loginButton = new Button("Login");
		GridPane.setConstraints(loginButton, 1, 2);
		loginButton.setOnAction(e -> handleLogin(stage, usernameInput.getText(), passwordInput.getText()));

		Button inviteCodeButton = new Button("Use Invite Code");
		GridPane.setConstraints(inviteCodeButton, 1, 3);
		inviteCodeButton.setOnAction(e -> showInviteCodePage(stage));

		grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, inviteCodeButton);

		Scene scene = new Scene(grid, 300, 200);
		stage.setScene(scene);
		stage.show();
		if(users.size() == 0)
			load();
		if (users.size() == 0) {
			oneTimePassword = generateRandomPassword(8);
			System.out.println("Welcome Admin. One time password is : " + oneTimePassword + 
					"\nThis password expires 12/31/2024");
		}
	}
	
	public static String generateRandomPassword(int length) {		//Generate a random 8 character 1 time password
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(length);

	    for (int i = 0; i < length; i++) {
	        int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	        }

	    return buffer.toString();
	}
	
	private void handleResetPage(Stage stage, User user)		//page to reset account
	{
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		TextField oneTimeInput = new TextField();
		Label prompt = new Label("Enter one-time password to reset account");
		oneTimeInput.setPromptText("Enter one-time password to reset account");
		Button enter = new Button("Enter");
		enter.setOnAction(e -> {
			LocalDateTime date = LocalDateTime.now();		//check if one time password has expired
			int year = date.getYear();
			if(year >= 2025)
			{
				showAlert("Error", "Password has expired!");
			}
			else {
				String text = oneTimeInput.getText();
				if(text.equals(oneTimeReset))			//check password
				{
					setNewPasswordPage(stage, user);
					oneTimeReset = "";
				}
				else {
					showAlert("Error", "Password does not match");
				}
			}
		});
		
		layout.getChildren().addAll(prompt, oneTimeInput, enter);
		Scene scene = new Scene(layout, 300, 300);
		stage.setScene(scene);
	}
	
	private void setNewPasswordPage(Stage stage, User user)		//set new password page
	{
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		
		//prompts for scene
		Label label1 = new Label("Enter New Password");
		Label label2 = new Label("Confirm Password");
		TextField textfield = new TextField();
		TextField textfield2 = new TextField();
		textfield.setPromptText("New Password");
		textfield2.setPromptText("New Password");
		Button enter = new Button("Enter");
		enter.setOnAction(e -> {
			if(textfield.getText().equals(textfield2.getText()))
			{
				user.setPassword(textfield.getText());
				user.setReset(false);
				showLoginPage(stage);
			}
			else
			{
				showAlert("Error", "Passwords do not match");
			}
		});
		
		layout.getChildren().addAll(label1, textfield, label2, textfield2, enter);
		Scene scene = new Scene(layout, 400,400);
		stage.setScene(scene);
		
	}
	
	private void handleLogin(Stage stage, String username, String password) {		//method to handle login attempt
		User user = users.get(username);
		if (users.size() == 0 && password.equals(oneTimePassword)) {
			showAccountCreationPage(stage, List.of("Admin"));
		}
		else {
		if (user != null && user.getPassword().equals(password)) {
			currentUser = user;
			if(user.getReset()) {
				handleResetPage(stage, user);
			}
			else if (!user.isSetupComplete()) {
				showAccountSetupPage(stage);
			} else if (user.getRoles().size() > 1) {
				showRoleSelectionPage(stage);
			} else {
				showHomePage(stage, user.getRoles().get(0));
			}
		} else {
			showAlert("Login Failed", "Invalid username or password.");
		}
		}
	}

	private void showInviteCodePage(Stage stage) {		//scene to show invite code page
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		
		Button goBack = new Button("Go Back");
		goBack.setOnAction(e -> showLoginPage(stage));
		
		Label inviteCodeLabel = new Label("Enter Invite Code:");
		TextField inviteCodeInput = new TextField();

		Button submitButton = new Button("Submit");
		submitButton.setOnAction(e -> handleInviteCode(stage, inviteCodeInput.getText()));

		layout.getChildren().addAll(inviteCodeLabel, inviteCodeInput, submitButton, goBack);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
	}
	

	private void handleInviteCode(Stage stage, String inviteCode) {		//method to handle invite code
		//oneTimePassword, oneTimeStudent, oneTimeInstructor, oneTimeAdmin, oneTimeStudentIns;
		
		if (inviteCode.equals(oneTimeAdmin) && !inviteCode.equals("")) {		//handles one time password for admin
			showAccountCreationPage(stage, List.of("Admin"));
			oneTimeAdmin = "";													//remove the one time password
		} else if (inviteCode.equals(oneTimeStudent) && !inviteCode.equals("")) {		//handles one time password for student
			showAccountCreationPage(stage, List.of("Student"));
			oneTimeStudent = "";														//removes one time password for student
		} else if (inviteCode.equals(oneTimeInstructor) && !inviteCode.equals("")) {
			showAccountCreationPage(stage, List.of("Instructor"));
			oneTimeInstructor = "";
		} else if(inviteCode.equals(oneTimePassword) && !inviteCode.equals("")) {
				showAccountCreationPage(stage, List.of("Admin"));
				oneTimePassword = null;
			}
		 else {
			showAlert("Invalid Code", "The invite code is invalid.");
		}
	}

	private void showAccountCreationPage(Stage stage, List<String> roles) {		//account user name and password creation
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));

		Label usernameLabel = new Label("Username:");
		TextField usernameInput = new TextField();

		Label passwordLabel = new Label("Password:");
		PasswordField passwordInput = new PasswordField();

		Label confirmPasswordLabel = new Label("Confirm Password:");
		PasswordField confirmPasswordInput = new PasswordField();

		Button createAccountButton = new Button("Create Account");
		createAccountButton.setOnAction(e -> {
			if (passwordInput.getText().equals(confirmPasswordInput.getText())) {
				User newUser = new User(usernameInput.getText(), passwordInput.getText(), roles);
				users.put(usernameInput.getText(), newUser);
				showLoginPage(stage);
			} else {
				showAlert("Password Mismatch", "Passwords do not match.");
			}
		});

		layout.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, confirmPasswordLabel, confirmPasswordInput, createAccountButton);
		Scene scene = new Scene(layout, 300, 300);
		stage.setScene(scene);
	}

	private void showAccountSetupPage(Stage stage) {		//account set up page
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));

		Label firstNameLabel = new Label("First Name:");
		TextField firstNameInput = new TextField();

		Label middleNameLabel = new Label("Middle Name:");
		TextField middleNameInput = new TextField();

		Label lastNameLabel = new Label("Last Name:");
		TextField lastNameInput = new TextField();

		Label preferredNameLabel = new Label("Preferred First Name (Optional):");
		TextField preferredNameInput = new TextField();

		Label emailLabel = new Label("Email:");
		TextField emailInput = new TextField();

		Button finishSetupButton = new Button("Finish Setup");		//once clicked with create new user with the text and set user to set up complete
		finishSetupButton.setOnAction(e -> {
			if(!firstNameInput.getText().equals("") && !middleNameInput.getText().equals("") && !lastNameInput.getText().equals("") && !emailInput.getText().equals("")) {	//check to see if required text fields are full
			currentUser.setFirstName(firstNameInput.getText());
			currentUser.setMiddleName(middleNameInput.getText());
			currentUser.setLastName(lastNameInput.getText());
			currentUser.setPreferredName(preferredNameInput.getText());
			currentUser.setEmail(emailInput.getText());
			currentUser.setSetupComplete(true);
			showHomePage(stage, currentUser.getRoles().get(0));	
		}
			else
				showAlert("Error", "Not all required fields have been filled");
		});
		layout.getChildren().addAll(firstNameLabel, firstNameInput, middleNameLabel, middleNameInput, lastNameLabel, lastNameInput, preferredNameLabel, preferredNameInput, emailLabel, emailInput, finishSetupButton);
		Scene scene = new Scene(layout, 400, 400);
		stage.setScene(scene);
	}

	private void showRoleSelectionPage(Stage stage) {		//If user has multiple rows show page to see which one they want to use for this login session
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));

		Label roleLabel = new Label("Select Role for this Session:");
		ComboBox<String> roleComboBox = new ComboBox<>();
		roleComboBox.getItems().addAll(currentUser.getRoles());

		Button selectRoleButton = new Button("Select Role");
		String thisRole = roleComboBox.getValue();
		if(thisRole.equals("")) {
		selectRoleButton.setOnAction(e -> showHomePage(stage, roleComboBox.getValue()));
		}
		layout.getChildren().addAll(roleLabel, roleComboBox, selectRoleButton);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
	}

	private void showHomePage(Stage stage, String role) {		//Original showHome page to be rerouted to the correct role
		if(role.equals("Student"))
		{
			showHomePageStudent(stage, role);
		}
		else if (role.equals("Instructor"))
		{
			showHomePageInstructor(stage, role);
		}
		else if(role.equals("Admin"))
			showAdminPage(stage, role);
		else
		{
			showAlert("Error", "Could not find role");		//error message in case role could not be determined
		}
	}
	
	private void showHomePageInstructor(Stage stage, String role) {		//home page for instructor role
		save();
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		Button articles = new Button("Articles");
		
		Label welcomeLabel = new Label("Welcome, " + currentUser.getDisplayName() + " (" + role + ")");
		Button logoutButton = new Button("Log Out");
		logoutButton.setOnAction(e -> {
			currentUser = null;
			showLoginPage(stage);
		});
		articles.setOnAction(e -> articleHomePage(stage));

		layout.getChildren().addAll(welcomeLabel, articles, logoutButton);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
	}
	
	private void showHomePageStudent(Stage stage, String role) {		//role page for student role
		save();
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));

		Label welcomeLabel = new Label("Welcome, " + currentUser.getDisplayName() + " (" + role + ")");
		Button logoutButton = new Button("Log Out");
		logoutButton.setOnAction(e -> {
			currentUser = null;
			showLoginPage(stage);
		});

		layout.getChildren().addAll(welcomeLabel, logoutButton);
		Scene scene = new Scene(layout, 300, 200);
		stage.setScene(scene);
	}

	private void showAlert(String title, String message) {		//alert method to be able to let user know if any errors
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	private void save()			//save database
	{
		try {
		      File myObj = new File("data.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } 
		      
		      FileWriter writer = new FileWriter("data.txt");
		      BufferedWriter myWriter = new BufferedWriter(writer);
		      myWriter.write("users");
		      myWriter.newLine();
		      User temp;
		      //Article tempA;
		      for(Map.Entry<String, User> entry : users.entrySet())		//write the users into data
		      {
		    	  temp = entry.getValue();
		    	  myWriter.write(temp.getUsername());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getPassword());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getEmail());
		    	  myWriter.newLine();
		    	  myWriter.write(String.join(":", temp.getRoles()));
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getFirstName());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getMiddleName());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getLastName());
		    	  myWriter.newLine();
		    	  myWriter.write(temp.getDisplayName());
		    	  myWriter.newLine();
		    	  if(temp.isSetupComplete())
		    		  myWriter.write("1");
		    	  else
		    		  myWriter.write("0");
		    	  myWriter.newLine();
		      }
		      myWriter.write("articles");
		      myWriter.newLine();
		      /*
		      for(Map.Entry<String, Articles> entry : articles.entrySet())			//write articles into data
		      {
		    	  tempA = entry.getValue();
		    	  
		      }*/
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	private boolean load()			//load database
	{
		try {
		      File myObj = new File("data.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		        return true;
		      } else {
		    	  
		        //System.out.println("File already exists.");
		        Scanner reader = new Scanner(myObj);		        
		        String line;
		        if(reader.hasNextLine())
		        	line = reader.nextLine();
		        while(reader.hasNextLine())
		        {
		        	line = reader.nextLine();
		        	if(line.equals("articles"))
		        		break;
		        	String un = line;
		        	line = reader.nextLine();
		        	String p = line;
		        	line = reader.nextLine();
		        	String e = line;
		        	line = reader.nextLine();
		        	String rol = line;
		        	String[] r = rol.split(":");
		        	List<String> ro = Arrays.asList(r);
		        	line = reader.nextLine();
		        	String fn = line;
		        	line = reader.nextLine();
		        	String mn = line;
		        	line = reader.nextLine();
		        	String ln = line;
		        	line = reader.nextLine();
		        	String pn = line;
		        	line = reader.nextLine();
		        	int i = Integer.parseInt(line);
		        	boolean setUp;
		        	if(i == 1)
		        		setUp = true;
		        	else
		        		setUp = false;
		        	User newUser = new User(un, p, ro);
		        	newUser.setEmail(e);
		        	newUser.setFirstName(fn);
		        	newUser.setLastName(ln);
		        	newUser.setMiddleName(mn);
		        	newUser.setPreferredName(pn);
		        	newUser.setSetupComplete(setUp);
		        	users.put(un, newUser);
		        	
		        }
		        while(reader.hasNextLine())
		        {
		        	
		        }
		        reader.close();
		        return false;
		        
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return true;
	}


	private static class User {			//user class
		private String username;
		private String password;
		private List<String> roles;
		private boolean setupComplete;
		private String firstName;
		private String middleName;
		private String lastName;
		private String preferredName;
		private String email;
		private boolean reset;

		public User(String username, String password, List<String> roles) {		//constructor for user class
			this.username = username;
			this.password = password;
			this.roles = roles;
			this.setupComplete = false;
			this.reset = false;
		}

		public void setPassword(String password)
		{
			this.password = password;
		}
		public String getPassword() {		//returns user password
			return password;
		}

		public List<String> getRoles() {		//returns user list of roles
			return roles;
		}

		public boolean isSetupComplete() {		//returns true if set up complete false if not
			return setupComplete;
		}

		public void setSetupComplete(boolean setupComplete) {		//method to set if set up has been completed
			this.setupComplete = setupComplete;
		}

		public void setFirstName(String firstName) {		//method to set first name
			this.firstName = firstName;
		}

		public void setMiddleName(String middleName) {		//method to set middle name
			this.middleName = middleName;
		}

		public void setLastName(String lastName) {		//method to set last name
			this.lastName = lastName;
		}

		public void setPreferredName(String preferredName) {		//method to set preferred name
			this.preferredName = preferredName;
		}

		public void setEmail(String email) {		//method to set email
			this.email = email;
		}
		public String getFirstName()		//returns first name
		{
			return this.firstName;
		}
		public String getLastName()			//returns last name
		{
			return this.lastName;
		}
		public String getMiddleName()		//returns middle name
		{
			return this.middleName;
		}
		public String getEmail()		//returns email
		{
			return this.email;
		}
		public void setReset(boolean resetState)
		{
			this.reset = resetState;
		}
		public boolean getReset()
		{
			return this.reset;
		}
		public String getDisplayName() {		//method to display preferred name or default to first name
			return preferredName != null && !preferredName.isEmpty() ? preferredName : firstName;
		}
		public String getUsername()
		{
			return this.username;
		}
		public String toString()		//display user
		{
			String userString = "Username: " + this.getUsername() + "\n"  
					+ "First name: " + this.getFirstName() + "\n"
					+ "Middle Name: " + this.getMiddleName() + "\n"
					+ "Last Name: " + this.getLastName() + "\n"
					+ "Email: " + this.getEmail() + "\n"
					+ "Roles: " + roles;
			return userString;
		}
	}
}
