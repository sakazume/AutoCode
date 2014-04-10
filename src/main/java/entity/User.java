package entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class User extends AbstraactEntity<QUser>{
	
	String password;
	String baseUrl;
	
	@OneToOne(mappedBy="user")
	Config config;
	
	public User() {
		super(QUser.user);
	}
	
	
}
