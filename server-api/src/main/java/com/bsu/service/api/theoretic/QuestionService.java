package com.bsu.service.api.theoretic;

import com.bsu.service.api.base.SearchableService;
import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;

import java.util.List;

/**
 * @author HomeUser
 *         Date: 14.5.13
 *         Time: 20.35
 */
public interface QuestionService extends SearchableService<QuestionDto> {
    List<QuestionDto> getForTopic(Integer selectedTopic);

    void saveAnswers(QuestionDto question, List<AnswerDto> answerList);

    List<AnswerDto> getAnswers(QuestionDto question);
}
