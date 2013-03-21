package com.bsu.server.dto.security;

import com.bsu.server.dto.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс содержит информацию о пользовательском аккаунте.
 */
@Entity
@Table(name = "user", schema = "public")
public class UserAccount extends BaseEntity implements UserDetails, Serializable {

    @NotBlank
    @Column(length = 50, nullable = false, unique = true, name = "login")
    private String username;

    @NotBlank
    @Column(length = 50, nullable = false, name = "password")
    private String password;

    @NotBlank
    @Column(nullable = false, name = "f_name")
    private String firstName;

    @NotBlank
    @Column(nullable = false, name = "l_name")
    private String lastName;

    @Column(name = "m_name")
    private String middleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private Set<UserRole> userRoles;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (userRoles != null) {
            HashSet<GrantedAuthority> res = new HashSet<GrantedAuthority>(userRoles.size());
            for (UserRole role : userRoles) {
                res.add(role);
            }
            return res;
        }
        return null;
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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}