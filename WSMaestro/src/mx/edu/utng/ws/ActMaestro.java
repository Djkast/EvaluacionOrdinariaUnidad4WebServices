package mx.edu.utng.ws;

public class ActMaestro {
	
	private int id;
	private String name;
	private String description;
	private String activ;
	
	public ActMaestro(int id, String name, String sinopsis, String type) {
		super();
		this.id = id;
		this.name = name;
		this.description = sinopsis;
		this.activ = type;
	}
	
	public ActMaestro() {
		this(0,"","","");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActiv() {
		return activ;
	}

	public void setActiv(String activ) {
		this.activ = activ;
	}

	@Override
	public String toString() {
		return "ActMaestro [id=" + id + ", name=" + name + ", description=" + description + ", activ=" + activ + "]";
	}
  
}