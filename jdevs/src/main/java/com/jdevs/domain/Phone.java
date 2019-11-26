package com.jdevs.domain;


import com.jdevs.domain.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "phone")
@Getter
@Setter
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String number;

    private String type;

    @ManyToOne
    @org.hibernate.annotations.ForeignKey(name = "person_id")
    private Person person;
}
