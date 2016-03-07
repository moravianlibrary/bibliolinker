package cz.mzk.recordmanager.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name=Inspiration.TABLE_NAME)
public class Inspiration extends AbstractDomainObject{
	
	public static final String TABLE_NAME = "inspiration";
	
	public Inspiration(){
	}
	
	public Inspiration(String name){
		setName(name);
	}
	
	@Column(name="name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
