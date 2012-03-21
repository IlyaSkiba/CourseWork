package org.jgeek.website.model.student.TheoreticTesting;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;

/**
 * Created by IntelliJ IDEA.
 * User: HomeUser
 * Date: 21.3.12
 * Time: 10.53
 * To change this template use File | Settings | File Templates.
 */
@Scope("request")
@Named("theoreticResult")
public class TheoreticTestingResultModel {
    private int mark = 100;

    public int getMark() {
        return mark;
    }
}
