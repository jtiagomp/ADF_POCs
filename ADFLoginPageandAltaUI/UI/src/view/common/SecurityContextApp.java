package view.common;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;

public class SecurityContextApp {
    public SecurityContextApp() {
        super();
    }

    public String[] getUserRoles() {
        return ADFContext.getCurrent()
                         .getSecurityContext()
                         .getUserRoles();
    }

    public boolean isUserInRole(String role) {

        for (String myRole : getUserRoles()) {
            if (myRole.equals(role)) {
                return true;
            }
        }

        return false;
    }


    public String getUserName() {
        ADFContext adfCtx = ADFContext.getCurrent();
        SecurityContext secCntx = adfCtx.getSecurityContext();
        return secCntx.getUserName();
    }

    public String getName() {
        ADFContext adfCtx = ADFContext.getCurrent();
        SecurityContext secCntx = adfCtx.getSecurityContext();

        return secCntx.getUserPrincipal().getName();
    }
}
