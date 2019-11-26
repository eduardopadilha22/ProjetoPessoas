package com.jdevs.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role implements GrantedAuthority { // nomes do acesso autoridade
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nomeRole;
    @Override
    public String getAuthority() {
        return this.nomeRole;
    }

    public String getNomeRole() { // ROLE_ADMIN, ROLE_GERENTE, ROLE_SECRETARIO
        return nomeRole;
    }

    public void setNomeRole(String nomeRole) {
        this.nomeRole = nomeRole;
    }
}
