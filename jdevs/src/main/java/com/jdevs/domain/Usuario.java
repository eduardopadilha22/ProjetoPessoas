package com.jdevs.domain;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario implements UserDetails {
    private static final long serialVersionUID= 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;
    private String senha;
    @OneToMany(fetch = FetchType.EAGER) // para sempre carregar do banco
    @JoinTable(name = "usuario_role", // nome da tabela
            joinColumns = @JoinColumn(name = "usuario_id", // cria um campo usuario
                    referencedColumnName = "id",
                    table = "usuario"), // cria tabela de acesso do usuario
            inverseJoinColumns = @JoinColumn(name = "role_id",// cria um campo role
                    referencedColumnName = "id",
                    table = "role"))//diz que é tabela role
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
