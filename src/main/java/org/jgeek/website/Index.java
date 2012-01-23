package org.jgeek.website;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.Serializable;

/**
 * User: Dmitry Leontyev
 * Date: 08.12.2010
 * Time: 22:14:22
 */
@Scope("request")
@Named("index")
public class Index implements Serializable {

    public String getHello() {
        return "Hello";
    }

}
