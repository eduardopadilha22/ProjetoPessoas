package com.jdevs.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name="person")
public class Person	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(max = 30)
    @NotNull(message = "Não pode ser nulo")
    @NotEmpty(message = "não pode ser vazio")
	private String name;
	
	@Size(max=30)
    @NotNull(message = "Sobrenome não poder nulo")
    @NotEmpty(message = "Sobrenome não pode nulo")
	private String lastname;
	
	@Column(columnDefinition = "int default 0")
    @Min(value = 18, message = "idade inválida")
	private int idade;

    @Column(columnDefinition = "int default 0")
	@OneToMany(mappedBy = "person", orphanRemoval = true, cascade = CascadeType.ALL) // remover person mesmo tendo relação com telefones
	private List<Phone> phones;

    private String cep;

    private String rua;

    private String bairro;

    private String cidade;

    private String uf;

    private String ibge;

    private String sexopessoa;
    
    @ManyToOne
    private Profession profission;
}
