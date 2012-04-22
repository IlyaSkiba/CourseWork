package website.model.global;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 22.4.12
 * Time: 12.57
 * To change this template use File | Settings | File Templates.
 */
@Scope("session")
@Named
public class StartPage {
    private List<String> images;

    public List<String> getImages() {
        images = new ArrayList<String>();
        images.add("study1.jpg");
        images.add("study2.jpg");
        images.add("study3.jpg");
        images.add("study4.jpg");
        images.add("study5.jpg");
        images.add("study7.jpg");
        return images;
    }
}
