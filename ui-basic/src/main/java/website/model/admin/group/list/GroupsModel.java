package website.model.admin.group.list;

import com.bsu.service.api.base.TransactionService;
import com.bsu.service.api.dto.CourseGroupDto;
import com.bsu.service.api.global.admin.CourseGroupService;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.global.admin.GroupService;
import com.bsu.service.api.global.admin.dto.UserGroupDto;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author HomeUser
 *         Date: 20.3.13
 *         Time: 23.49
 */
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
@Named
public class GroupsModel extends LazyDataModel<GroupListEntity> {
    @Autowired
    private GroupService groupService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CourseGroupService courseGroupService;
    private GroupListEntity deleteGroup;

    @Override
    public List<GroupListEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                      Map<String, String> filters) {
        setRowCount(groupService.count(filters));
        return convert(groupService.search(filters, sortField, sortOrder.name(), first, pageSize));
    }

    @Override
    public GroupListEntity getRowData(String rowKey) {
        return convert(Arrays.asList(groupService.getById(Integer.parseInt(rowKey)))).get(0);
    }

    @Override
    public Object getRowKey(GroupListEntity object) {
        return object.getId();
    }

    private List<GroupListEntity> convert(List<UserGroupDto> searchResult) {
        return Lists.transform(searchResult, new ConvertEntityFunction());
    }

    private class ConvertEntityFunction implements Function<UserGroupDto, GroupListEntity> {

        @Override
        public GroupListEntity apply(UserGroupDto input) {
            GroupListEntity output = new GroupListEntity();
            output.setId(input.getId());
            output.setName(input.getGroupName());
            output.setCourses(transactionService.callInTransaction(
                    new GetCourseListExecutor(courseGroupService.getCourses(input))));
            return output;
        }
    }

    private class GetCourseListExecutor implements Callable<List<String>> {
        private List<CourseGroupDto> courseIds;

        private GetCourseListExecutor(List<CourseGroupDto> courseIds) {
            this.courseIds = courseIds;
        }

        @Override
        public List<String> call() throws Exception {
            return Lists.transform(courseIds, new Function<CourseGroupDto, String>() {
                @Override
                public String apply(@Nullable CourseGroupDto input) {
                    return courseService.getById(input.getCourseId()).getCourseName();
                }
            });
        }
    }

    public void setDeleteGroup(GroupListEntity deleteGroup) {
        this.deleteGroup = deleteGroup;
    }

    public void delete() {
        groupService.delete(new UserGroupDto() {
            {
                setId(deleteGroup.getId());
            }
        });
    }
}
