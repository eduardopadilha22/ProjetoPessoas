package com.jdevs.domain;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profession")
@Setter
@Getter
public class Profession {
	
	@Id
	private Long id;
	
	private String nome;
	
}
