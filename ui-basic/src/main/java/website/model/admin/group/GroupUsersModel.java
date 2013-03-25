package website.model.admin.group;

import com.bsu.service.api.global.admin.UserService;
import com.bsu.service.api.global.admin.dto.UserDto;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author HomeUser
 *         Date: 29.1.13
 *         Time: 23.38
 */

public class GroupUsersModel extends LazyDataModel<UserDto> implements Serializable {
    private UserService userService;

    public GroupUsersModel(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        setRowCount(userService.count(filters));
        return userService.search(filters, sortField, sortOrder.name(), first, pageSize);
    }


    public UserDto getRowData(String rowKey) {
        return userService.getById(Integer.valueOf(rowKey));
    }

    public Object getRowKey(UserDto object) {
        return object.getUserId();
    }
}
