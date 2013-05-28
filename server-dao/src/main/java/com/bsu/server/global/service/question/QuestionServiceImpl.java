package com.bsu.server.global.service.question;

import com.bsu.server.assembler.AnswerAssembler;
import com.bsu.server.assembler.QuestionDtoAssembler;
import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.global.service.base.BaseSearchableServiceImplImpl;
import com.bsu.server.theoretic.test.controller.AnswerController;
import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.controller.TestController;
import com.bsu.server.theoretic.test.dto.AnswerEntity;
import com.bsu.server.theoretic.test.dto.QuestionEntity;
import com.bsu.server.theoretic.test.dto.TestEntity;
import com.bsu.service.api.dto.AnswerDto;
import com.bsu.service.api.dto.QuestionDto;
import com.bsu.service.api.theoretic.QuestionService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 14.5.13
 *         Time: 20.44
 */
@Service
@Transactional(readOnly = true)
public class QuestionServiceImpl extends BaseSearchableServiceImplImpl<QuestionDto, QuestionEntity> implements QuestionService {
    @Autowired
    private QuestionController questionController;
    @Autowired
    private QuestionDtoAssembler questionDtoAssembler;
    @Autowired
    private TestController testController;
    @Autowired
    private AnswerAssembler answerAssembler;
    @Autowired
    private AnswerController answerController;

    @Override
    protected BaseController<QuestionEntity> getController() {
        return questionController;
    }

    @Override
    protected BaseConverter<QuestionDto, QuestionEntity> getConverter() {
        return questionDtoAssembler;
    }

    @Override
    public List<QuestionDto> getForTopic(Integer selectedTopic) {
        TestEntity test = testController.getTestFromTheme(selectedTopic);
        if (test == null) {
            return Lists.newArrayList();
        }
        return convertList(questionController.getQuestionsForTest(test.getId()));
    }

    @Transactional(readOnly = false)
    @Override
    public void saveAnswers(QuestionDto question, List<AnswerDto> answerList) {
        final QuestionEntity entity = getConverter().convert(question);
        final boolean[] hasWrongAnswers = {entity.getQuestionType() != null && entity.getQuestionType() > 0};

        entity.getAnswers().addAll(Lists.transform(answerList, new Function<AnswerDto, AnswerEntity>() {
            @Override
            public AnswerEntity apply(AnswerDto input) {
                AnswerEntity answerEntity = answerAssembler.convert(input);
                answerEntity.setQuestionEntity(entity);
                hasWrongAnswers[0] = hasWrongAnswers[0] || (!input.isRight());
                if (answerEntity.getId() == null) {
                    answerEntity = answerController.create(answerEntity);
                } else {
                    answerEntity = answerController.update(answerEntity);
                }
                return answerEntity;
            }
        }));
        entity.setQuestionType(hasWrongAnswers[0] ? 1 : 0);
        getController().update(entity);
    }

    @Override
    public List<AnswerDto> getAnswers(QuestionDto question) {
        List<AnswerEntity> answerEntities = getConverter().convert(question).getAnswers();
        List<AnswerDto> answers = Lists.transform(answerEntities, new Function<AnswerEntity, AnswerDto>() {
            @Nullable
            @Override
            public AnswerDto apply(@Nullable AnswerEntity input) {
                return answerAssembler.convert(input);
            }
        });
        if (answers.size() > 0) {
            answers.get(0);
        }
        return answers;
    }
}
