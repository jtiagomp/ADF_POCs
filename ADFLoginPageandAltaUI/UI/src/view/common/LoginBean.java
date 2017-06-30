package view.common;



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

import view.adftools.ADFUtils;

import weblogic.servlet.security.ServletAuthentication;

import weblogic.security.SimpleCallbackHandler;
import weblogic.security.services.Authentication;

public class LoginBean implements Serializable{
    @SuppressWarnings("compatibility:2992071069688435404")
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(LoginBean.class.getName());
    private String userName;
    private String password;

    public LoginBean() {
        super();
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
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
        @SuppressWarnings("deprecation")
        CallbackHandler handler = new SimpleCallbackHandler(this.userName, this.password.getBytes());
        try {
            Subject sub = Authentication.login(handler);
            ServletAuthentication.runAs(sub, request);
            ServletAuthentication.generateNewSessionID(request);
            String successUrl = "/adfAuthentication?success_url=/faces/welcome.jsf";
            HttpServletResponse response = (HttpServletResponse) fctx.getExternalContext().getResponse();
            RequestDispatcher dispatcher = request.getRequestDispatcher(successUrl);
            dispatcher.forward(request, response);
            fctx.responseComplete();
        } catch (Exception e) {
            ADFUtils.showFacesMessage(FacesMessage.SEVERITY_ERROR, "Login data error. Please confirm your login and password!");
            logger.severe("Login Exception with username:  "+ getUserName() );
        }
    }


    public String onLogout() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        String url = ectx.getRequestContextPath() + "/adfAuthentication?logout=true&end_url=/faces/login.jsf";
        try {
            ectx.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fctx.responseComplete();
        return null;

    }
}
