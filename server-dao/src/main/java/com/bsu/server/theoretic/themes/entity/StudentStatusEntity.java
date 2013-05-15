package com.bsu.server.theoretic.themes.entity;

import com.bsu.server.dto.BaseEntity;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.dto.security.UserAccount;

import javax.persistence.*;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "theme_status", schema = "public")
public class StudentStatusEntity extends BaseEntity {
    @OneToOne
    private UserAccount user;
    @ManyToOne
    private ThemeEntity parentTheme;
    @Column(name = "status")
    private Status status;
    @Column(name = "try_count")
    private Integer countOfTries;

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public ThemeEntity getParentTheme() {
        return parentTheme;
    }

    public void setParentTheme(ThemeEntity parentTheme) {
        this.parentTheme = parentTheme;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getCountOfTries() {
        return countOfTries;
    }

    public void setCountOfTries(Integer countOfTries) {
        this.countOfTries = countOfTries;
    }

    public enum Status {
        NOT_STARTED, IN_PROGRESS, FINISHED
    }
}
