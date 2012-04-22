package website.model.student;


import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 24.1.12
 * Time: 18.04
 * To change this template use File | Settings | File Templates.
 */
@Scope("session")
@Named("studentModel")
public class StudentMainModel implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
