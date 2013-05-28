package com.bsu.server.theoretic.themes.controller;

import com.bsu.server.controller.ThemeController;
import com.bsu.server.controller.UserController;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.theoretic.themes.entity.QStudentStatusEntity;
import com.bsu.server.theoretic.themes.entity.StudentStatusEntity;
import com.google.common.collect.Lists;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.bsu.server.theoretic.themes.entity.StudentStatusEntity.Status;

/**
 * @author Ilya Skiba
 */
@Service
public class StudentStatusController extends BaseController<StudentStatusEntity> {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserController userController;
    @Autowired
    private ThemeController themeController;

    @Override
    protected Class<StudentStatusEntity> getEntityClass() {
        return StudentStatusEntity.class;
    }

    public List<ThemeEntity> filterThemesByAccessibility(List<ThemeEntity> nonFiltered,
                                                         ThemeEntity.Significance significance, Integer userId) {
        return filterDependencies(nonFiltered, significance, userId);
    }

    private List<ThemeEntity> filterDependencies(List<ThemeEntity> filterData, ThemeEntity.Significance significance, Integer userId) {
        List<ThemeEntity> themeEntities = Lists.newArrayList();
        for (ThemeEntity theme : filterData) {
            boolean isAcceptable = true;
            if (theme.getRelatedTest() == null) {
                continue;
            }
            if (theme.getSignificance().lower(significance)) {
                continue;
            }
            for (ThemeEntity parentTheme : theme.getParentThemes()) {
                if (parentTheme.getSignificance().lowerEqual(significance)) {
                    StudentStatusEntity statusEntity = getByThemeId(parentTheme.getId(), userId);
                    if (statusEntity != null && statusEntity.getStatus() == Status.IN_PROGRESS) {
                        isAcceptable = false;
                        break;
                    }
                }
            }
            if (isAcceptable) {
                themeEntities.add(theme);
            }
        }
        return themeEntities;
    }

    public StudentStatusEntity getByThemeId(Integer parentThemeId, Integer userId) {
        JPQLQuery query = new JPAQuery(em);
        query = query.from(QStudentStatusEntity.studentStatusEntity).where(QStudentStatusEntity.studentStatusEntity
                .parentTheme.id.eq(parentThemeId).and(QStudentStatusEntity.studentStatusEntity.user.id.eq(userId)));
        return query.singleResult(QStudentStatusEntity.studentStatusEntity);
    }

    @Transactional(readOnly = false)
    public void increaseTryCount(Integer themeId, Integer userId, Integer testResult) {
        updateStatus(themeId, userId, testResult >= 40, Status.FINISHED);
        for (ThemeEntity entity : themeController.getById(themeId).getParentThemes()) {
            updateParents(entity.getId(), userId, testResult >= 40);
        }
    }

    private void updateParents(Integer themeId, Integer userId, boolean successMarker) {
        ThemeEntity root = themeController.getById(themeId);
        List<ThemeEntity> childrenThemes = themeController.getChildrenThemes(root);
        boolean needUpdate = false;
        for (ThemeEntity childrenTheme : childrenThemes) {
            StudentStatusEntity entity = getByThemeId(childrenTheme.getId(), userId);
            boolean isSuccessful = entity == null || (entity.getStatus() != Status.IN_PROGRESS);
            needUpdate = needUpdate || (!(isSuccessful ^ successMarker));
            if (needUpdate) {
                break;
            }
        }
        if (needUpdate) {
            updateStatus(themeId, userId, successMarker, Status.NOT_STARTED);
            for (ThemeEntity parent : root.getParentThemes()) {
                updateParents(parent.getId(), userId, successMarker);
            }
        }
    }

    private void updateStatus(Integer themeId, Integer userId, boolean successful, Status successStatus) {
        StudentStatusEntity entity = getByThemeId(themeId, userId);
        if (entity != null) {
            entity.setCountOfTries(successStatus == Status.NOT_STARTED ? 0 : entity.getCountOfTries() + 1);
            entity.setStatus(successful ? successStatus : Status.IN_PROGRESS);
            update(entity);
        } else {
            entity = new StudentStatusEntity();
            entity.setUser(userController.getById(userId));
            entity.setParentTheme(themeController.getById(themeId));
            entity.setStatus(successful ? successStatus : Status.IN_PROGRESS);
            entity.setCountOfTries(successStatus == Status.NOT_STARTED ? 0 : 1);
            create(entity);
        }
    }
}
