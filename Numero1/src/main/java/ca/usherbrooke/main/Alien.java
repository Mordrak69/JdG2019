package ca.usherbrooke.main;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "alien")
@SequenceGenerator(name = "category_sequence", initialValue = 1, allocationSize = 1, sequenceName = "category_sequence")
public class Alien {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private Long age;

	@ManyToMany
	@JoinTable(name = "alien_friends", joinColumns = {@JoinColumn(name = "fk_id_alien1")}, inverseJoinColumns = {@JoinColumn(name = "fk_id_alien2")})
	private List<Alien> friends;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public List<Alien> getFriends() {
		return friends;
	}

	public void setFriends(List<Alien> friends) {
		this.friends = friends;
	}
}
