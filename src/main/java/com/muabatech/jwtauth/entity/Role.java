package com.muabatech.jwtauth.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Roles")
public class Role {
	@Id
	@SequenceGenerator(
			name="roles_sequence",
			sequenceName="roles_sequence",
			allocationSize=1)
	@GeneratedValue(
			strategy = GenerationType.AUTO,
			generator = "roles_sequence")
	private Long id;
	private String name;
	@ManyToMany(mappedBy="roles")
	@Fetch(value=FetchMode.SELECT)
	@JsonIgnore
	private Set<User> user = new HashSet<>();
	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	public Role(String name) {
		this.name = name;
	}
	
}
