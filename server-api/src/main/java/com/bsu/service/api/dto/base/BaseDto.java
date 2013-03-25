package com.bsu.service.api.dto.base;

import java.io.Serializable;

/**
 * @author HomeUser
 *         Date: 25.3.13
 *         Time: 22.05
 */
public class BaseDto implements Serializable {
    private Integer id;
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
