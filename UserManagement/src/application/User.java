package application;

import java.util.List;

public class User {			//user class
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