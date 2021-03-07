package br.edu.ifba.jogomemoria.infrastructure.security.model;

import br.edu.ifba.jogomemoria.user.enums.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserSecurity implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSecurity() {

    }

    public UserSecurity(Long id, String username, String password, List<Authority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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