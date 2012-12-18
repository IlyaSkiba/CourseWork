package website.model.common;

import javax.faces.context.FacesContext;

/**
 * @author Ilya SKiba
 * @created 26/11/12
 */
public final class ServletUtils {
    public static String buildPath(String smallPath) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + smallPath;
    }
}
