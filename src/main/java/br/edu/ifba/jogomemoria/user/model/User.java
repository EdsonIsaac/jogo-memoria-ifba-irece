package br.edu.ifba.jogomemoria.user.model;

import br.edu.ifba.jogomemoria.infrastructure.model.AbstractEntity;
import br.edu.ifba.jogomemoria.user.enums.Authority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_user")
public class User extends AbstractEntity {

    @Column(length = 100)
    private String name;

    @Column(length = 25)
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_user_authorities")
    private List<Integer> authorities;

    @Transient
    private String confirmPassword;

    public User() {
        this.authorities = new ArrayList<>();
        this.authorities.addAll(
            Arrays.asList(
                Authority.PANEL.getCode(),
                Authority.PANEL_CARDS.getCode()
            )
        );
    }

    public User(String name, String username, String password, List<Integer> authorities, String confirmPassword) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<Authority> getAuthorities() {
        return authorities.stream().map(x -> Authority.toEnum(x)).collect(Collectors.toList());
    }

    public void setAuthorities(List<Integer> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority (Authority authority) {
        this.authorities.add(authority.getCode());
    }

    @Override
    public String toString() {
        return
            "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                ", confirmPassword='" + confirmPassword + '\'' +
            '}'
        ;
    }
}