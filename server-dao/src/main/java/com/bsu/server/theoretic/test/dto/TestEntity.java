package com.bsu.server.theoretic.test.dto;

import com.bsu.server.dto.BaseEntity;
import com.bsu.server.dto.ThemeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Ilya Skiba
 *         This class represents global info about theoretic test
 */
@Entity
@Table(name = "global_test", schema = "public")
public class TestEntity extends BaseEntity implements Serializable {

    @OneToOne
    private ThemeEntity relatedTheme;

    @Column(name = "points_count")
    private Integer pointsCount;

    @Column(name = "question_count")
    private Integer questionCount;

    public ThemeEntity getRelatedTheme() {
        return relatedTheme;
    }

    public void setRelatedTheme(ThemeEntity relatedTheme) {
        this.relatedTheme = relatedTheme;
    }

    public Integer getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(Integer pointsCount) {
        this.pointsCount = pointsCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

}
