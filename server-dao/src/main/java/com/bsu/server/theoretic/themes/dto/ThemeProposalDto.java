package com.bsu.server.theoretic.themes.dto;

import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.theoretic.test.dto.AnswerDto;
import com.bsu.server.theoretic.test.dto.QuestionDto;

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
    private Set<QuestionDto> proposalQuestions;

    @OneToMany
    Set<AnswerDto> proposalAnswers;

    @ManyToOne
    private ThemeEntity assignedTheme;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<QuestionDto> getProposalQuestions() {
        return proposalQuestions;
    }

    public void setProposalQuestions(Set<QuestionDto> proposalQuestions) {
        this.proposalQuestions = proposalQuestions;
    }

    public Set<AnswerDto> getProposalAnswers() {
        return proposalAnswers;
    }

    public void setProposalAnswers(Set<AnswerDto> proposalAnswers) {
        this.proposalAnswers = proposalAnswers;
    }

    public ThemeEntity getAssignedTheme() {
        return assignedTheme;
    }

    public void setAssignedTheme(ThemeEntity assignedTheme) {
        this.assignedTheme = assignedTheme;
    }
}
