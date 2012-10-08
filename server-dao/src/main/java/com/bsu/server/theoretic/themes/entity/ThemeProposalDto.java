package com.bsu.server.theoretic.themes.entity;

import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.theoretic.test.dto.AnswerEntity;
import com.bsu.server.theoretic.test.dto.QuestionEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "theme_proposal", schema = "public")
public class ThemeProposalDto {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany
    private Set<QuestionEntity> proposalQuestions;

    @OneToMany
    Set<AnswerEntity> proposalAnswers;

    @ManyToOne
    private ThemeEntity assignedTheme;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<QuestionEntity> getProposalQuestions() {
        return proposalQuestions;
    }

    public void setProposalQuestions(Set<QuestionEntity> proposalQuestions) {
        this.proposalQuestions = proposalQuestions;
    }

    public Set<AnswerEntity> getProposalAnswers() {
        return proposalAnswers;
    }

    public void setProposalAnswers(Set<AnswerEntity> proposalAnswers) {
        this.proposalAnswers = proposalAnswers;
    }

    public ThemeEntity getAssignedTheme() {
        return assignedTheme;
    }

    public void setAssignedTheme(ThemeEntity assignedTheme) {
        this.assignedTheme = assignedTheme;
    }
}
