package bigcloneeval.evaluation.database;

public class Tool {
	
	private long id;
	private String name;
	private String description;
	private String author;
	private String homepagelink;
	private String citation;
	
		
	public Tool() {
		super();
	}
	
	public Tool(long id) {
		super();
		this.id = id;
	}



	public Tool(long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Tool(long id, String name, String author,String description, String homepagelink, String citation) {
		super();
		this.id = id;
		this.name = name;
		this.author=author;
		this.description = description;
		this.homepagelink=homepagelink;
		this.citation=citation;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	public String getDescription() {
		return description;
	}
	
	

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getHomepagelink() {
		return homepagelink;
	}

	public void setHomepagelink(String homepagelink) {
		this.homepagelink = homepagelink;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tool other = (Tool) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
