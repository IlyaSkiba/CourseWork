package website.model.teacher.statistics.model;

import com.bsu.service.api.dto.StudentResultDto;

import java.io.Serializable;

/**
 * @author HomeUser
 *         Date: 3.6.13
 *         Time: 20.32
 */
public class ThemeModel implements Serializable {
    private Integer userId;
    private String userName;
    private Integer result;

    public ThemeModel() {

    }

    public ThemeModel(StudentResultDto result, int userId) {
        setResult(result.getResultScore());
        setUserId(userId);
        setUserName(result.getUserName());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
