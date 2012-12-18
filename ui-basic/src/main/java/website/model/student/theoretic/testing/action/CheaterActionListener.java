package website.model.student.theoretic.testing.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import website.model.student.theoretic.testing.TheoreticTestingModel;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.inject.Named;
import java.io.IOException;

/**
 * @author Ilya Skiba
 */
@Scope("request")
@Named("cheatListener")
public class CheaterActionListener implements ActionListener {

    @Autowired
    private TheoreticTestingModel theoreticTestingModel;

    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        theoreticTestingModel.setCheaterState(theoreticTestingModel.getCheaterState() + 1);
        if (theoreticTestingModel.getCheaterState() > 1) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("end_test.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
