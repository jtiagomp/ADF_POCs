/*
 *  Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 */


/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    João Tiago Pereira     Aug 4, 2017 - Creation
 */

/**
 *  @version $Header: LoginBean.java Aug 4, 2017.2:43:22 PM João Tiago Pereira  Exp $
 *  @author  João Tiago Pereira
 *  @since   release specific (what release of product did this appear in)
 */

package com.app.common.login;
/**
 *
 * @author João Tiago Pereira
 */

import com.app.common.BusinessConstants;
import com.app.common.utils.ADFUtils;
import java.io.IOException;
import java.io.Serializable;

import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

import javax.security.auth.login.LoginException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;

import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import weblogic.security.SimpleCallbackHandler;

import weblogic.servlet.security.ServletAuthentication;

import weblogic.security.services.Authentication;

public class LoginBean implements Serializable {
    @SuppressWarnings("compatibility:2992071069688435404")
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(LoginBean.class.getName());
    private String userName;
    private String password;
    private RichRegion dynamicRegionBinding;

    public LoginBean() {
        super();

        ADFContext adfCtx = ADFContext.getCurrent();
        SecurityContext secCntx = adfCtx.getSecurityContext();
        logger.warning("User Logged: "+ secCntx.getUserName());
        if (!secCntx.isAuthenticated()) {
            loginbyContext();
        }

    }

    private void loginbyContext() {

        String getParam = FacesContext.getCurrentInstance()
                                      .getExternalContext()
                                      .getRequestParameterMap()
                                      .get("id");

        if (getParam != null) {
            String param =
                Encryptor.decrypt(BusinessConstants.getEncryptorkey(), BusinessConstants.getEncryptorinitVector(),
                                  getParam);

            if (param != null && param.contains(",")) {
                String[] params = param.split(",");
                String userName = params[0];
                String password = params[1];
                System.out.println("username " + userName);
                System.out.println("password " + password);
                if (doLogin(userName, password)) {
                    //redirect to task flow account
//                    PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
//                    String accountTaskflowId = "/tf/account/UserAccount-TF.xml#UserAccount-TF";
//                    _propertyChangeSupport.firePropertyChange("taskFlowId", ConsoleManagedBean.DEFAULT_TASKFLOW_ID,
//                                                              accountTaskflowId);
//
//                    ADFUtils.refreshBindingComponent(dynamicRegionBinding);
                } else {
                    ADFUtils.showFacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid URL Token!");
                }
            } else {
                ADFUtils.showFacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid URL Token!");
            }
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public void doLogin(ActionEvent event) {
        doLogin(this.userName, this.password);
    }

    private boolean doLogin(String userName, String password) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
        @SuppressWarnings("oracle.jdeveloper.java.constructor-deprecated")
        CallbackHandler handler = new SimpleCallbackHandler(userName, password);
        try {
            Subject sub = Authentication.login(handler);
            ServletAuthentication.runAs(sub, request);
            ServletAuthentication.generateNewSessionID(request);
            String successUrl = "/adfAuthentication?success_url=/faces/index";
            HttpServletResponse response = (HttpServletResponse) fctx.getExternalContext().getResponse();
            RequestDispatcher dispatcher = request.getRequestDispatcher(successUrl);
            dispatcher.forward(request, response);
            fctx.responseComplete();
        } catch (Exception e) {
            ADFUtils.showFacesMessage(FacesMessage.SEVERITY_ERROR,
                                      "Login data error. Please confirm your login and password!");
            logger.severe("Login Exception with username:  " + getUserName());
            return false;
        }
        return true;
    }


    public String onLogout() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        String url = ectx.getRequestContextPath() + "/adfAuthentication?logout=true&end_url=/faces/login";
        try {
            ectx.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fctx.responseComplete();
        return null;

    }

}
