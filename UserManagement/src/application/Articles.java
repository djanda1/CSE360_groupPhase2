package application;
public class Articles {
	private String title, description, keywords, authors, body, references, group;
	
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
		this.authors = authors;
		this.keywords = keywords;
		this.description = description;
		this.references = references;
		int id = Integer.parseInt(title);
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
	
}