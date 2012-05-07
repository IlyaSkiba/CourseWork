package com.bsu.server.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "user_group", schema = "public")
public class UserGroupDto implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer id;

    @Column(name = "group_name")
    public String groupName;

    @ManyToMany(mappedBy = "assignedGroups")
    public List<ThemeDto> availableThemes;

    public Integer getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ThemeDto> getAvailableThemes() {
        return availableThemes;
    }

    public void setAvailableThemes(List<ThemeDto> availableThemes) {
        this.availableThemes = availableThemes;
    }
}
