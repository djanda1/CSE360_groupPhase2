
package application;

public class Articles {
	private String title, description, keywords, authors, body, references, group;
	private int id;
	
	//default constructor
	public Articles()
	{
	}
	
	//overloaded constructor
	public Articles(String title, String description, String keywords, String authors, String body, String references, String group)
	{
		this.title = title;
		this.authors = authors;
		this.body = body;
		this.keywords = keywords;
		this.description = description;
		this.references = references;
		id = 0;
		for(int i = 0; i < title.length(); i++)
		{
			char ch = title.charAt(i);
			this.id += (int)ch;
		}
		this.group = group;
	}
	
	
	//accessors
	public String getTitle()
	{
		return this.title;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getKeywords()
	{
		return this.keywords;
	}
	
	public String getAuthors()
	{
		return this.authors;
	}
	
	public String getBody()
	{
		return this.body;
	}
	
	public String getReferences()
	{
		return this.references;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getGroup()
	{
		return this.group;
	}
	
	public void setBody(String body)
	{
		this.body = body;
	}
	
	public String toString()
	{
		return "Title: " + this.title + "\n"
				+ "Authors: " + this.authors + "\n"
				+ "Description: " + this.description + "\n"
				+ "Group: " + this.group + "\n"
				+ "ID: " + this.id + "\n";
	}
	
	public String toStringFull()
	{
		return "Title: " + this.title + "\n"
				+ "Authors: " + this.authors + "\n"
				+ "Description: " + this.description + "\n"
				+ "Keywords: " + this.keywords + "\n" 
				+ "Body: " + this.body + "\n"
				+ "References: " + this.references + "\n";
				
	}
	
}
