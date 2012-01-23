package org.jgeek.website.model.security;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: Dmitry Leontyev
 * Date: 26.12.10
 * Time: 23:26
 */
@Entity
@Table(name = "USER_PERSISTENCE_LOGIN")
public class UserPersistentLogin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, unique = true, length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String token;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
}