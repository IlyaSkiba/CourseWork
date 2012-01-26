package org.jgeek.website.model.student.TheoreticTesting;

import org.jgeek.website.model.student.StudentMainModel;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 24.1.12
 * Time: 18.54
 * To change this template use File | Settings | File Templates.
 */

@Scope("session")
@Named("theoretic")
public class TheoreticTestingModel {
    private static final String TEMPLATE_URL = "teoretic_main.xhtml";
    @Inject
    private StudentMainModel studentMainModel;

    public void load() {
        studentMainModel.setMessage(TEMPLATE_URL);

    }

}
