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
 *  @version $Header: NavigationBean.java Aug 4, 2017.2:43:43 PM João Tiago Pereira  Exp $
 *  @author  João Tiago Pereira
 *  @since   release specific (what release of product did this appear in)
 */

package com.app.common.navigation;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.Serializable;


import oracle.adf.controller.TaskFlowId;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;


public class NavigationBean implements Serializable {
    @SuppressWarnings("compatibility:3919165261931596829")
    private static final long serialVersionUID = -3108598285098344139L;


    private String taskFlowId = DEFAULT_TASKFLOW_ID;

    private static final String DEFAULT_TASKFLOW_ID = "/WEB-INF/welcome-TF.xml#welcome-TF";
    private transient PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);



    public NavigationBean() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public void setTaskFlowId(String taskFlowId) {
        System.out.println("taskFlowId  " + taskFlowId);
        String oldTaskFlowId = this.taskFlowId;
        this.taskFlowId = taskFlowId;
        _propertyChangeSupport.firePropertyChange("taskFlowId", oldTaskFlowId, taskFlowId);
    }

    public String getTaskFlowId() {
        return taskFlowId;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.removePropertyChangeListener(l);
    }

    public DnDAction dropTargetDropListener(DropEvent dropEvent) {
        // Add event code here...
        return DnDAction.NONE;
    }
}
