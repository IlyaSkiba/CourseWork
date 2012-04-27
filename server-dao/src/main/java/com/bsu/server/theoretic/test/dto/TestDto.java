package com.bsu.server.theoretic.test.dto;

import com.bsu.server.dto.ThemeDto;

import javax.persistence.*;

/**
 * @author Ilya Skiba
 *         This class represents global info about theoretic test
 */
@Entity
@Table(name = "global_test", schema = "public")
public class TestDto {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @OneToOne
    private ThemeDto relatedTheme;

    @Column(name = "points_count")
    private Integer pointsCount;

    @Column(name = "question_count")
    private Integer questionCount;

    public ThemeDto getRelatedTheme() {
        return relatedTheme;
    }

    public void setRelatedTheme(ThemeDto relatedTheme) {
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

    public Integer getId() {
        return id;
    }
}
