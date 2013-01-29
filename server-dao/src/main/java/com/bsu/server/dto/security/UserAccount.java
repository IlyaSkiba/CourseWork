package com.bsu.server.dto.security;

import com.bsu.server.dto.UserGroupEntity;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс содержит информацию о пользовательском аккаунте.
 */
@Entity
@Table(name = "user", schema = "public")
public class UserAccount implements UserDetails, Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role_list",
            joinColumns =
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns =
            @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<UserRole> userRoles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_group_list",
            joinColumns =
            @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns =
            @JoinColumn(name = "group_id", referencedColumnName = "id")
    )
    private List<UserGroupEntity> userGroups;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<UserGroupEntity> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroupEntity> userGroups) {
        this.userGroups = userGroups;
    }
}