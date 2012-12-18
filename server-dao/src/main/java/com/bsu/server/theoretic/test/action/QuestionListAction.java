package com.bsu.server.theoretic.test.action;

import com.bsu.server.theoretic.test.dto.QuestionEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ilya Skiba
 */
public class QuestionListAction {

    public List<QuestionEntity> getIdsByPoints(List<QuestionEntity> questionEntities, Integer pointsCount) {
        List<QuestionEntity> questions = shuffle(questionEntities);
        if (pointsCount == null || pointsCount <= 0) {
            return Collections.emptyList();
        }
        int[] questSum = new int[pointsCount + 1];
        questSum[0] = 1;
        int countOfQuestion = questions.size();
        for (int i = pointsCount + 1; i > 0; i++) {
            for (int j = 0; j < countOfQuestion; j++) {
                if (i >= questions.get(j).getWeight() && questSum[i - questions.get(j).getWeight()] > 0) {
                    questSum[i] = j + 1;
                }
            }
        }
        if (questSum[pointsCount] == 0) {
            return questions;
        }

        List<QuestionEntity> result = new ArrayList<QuestionEntity>();
        int index = pointsCount;
        while (index > 0) {
            result.add(questions.get(questSum[index - 1]));
            index = questSum[index];
        }
        return result;
    }

    public List<QuestionEntity> shuffle(List<QuestionEntity> questions) {
        List<QuestionEntity> result = new ArrayList<QuestionEntity>(questions);
        Collections.shuffle(result);
        return result;
    }
}
