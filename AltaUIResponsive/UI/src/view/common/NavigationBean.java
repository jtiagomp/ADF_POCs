package view.common;

import java.beans.PropertyChangeSupport;

import java.io.Serializable;

import oracle.adf.controller.TaskFlowId;

public class NavigationBean implements Serializable {
    @SuppressWarnings("compatibility:4239977873368972068")
    private static final long serialVersionUID = 1L;
    
    
    
    private static final String DEFAULT_TASKFLOW_ID = "/WEB-INF/welcome-TF.xml#welcome-TF";
    private String taskFlowId = DEFAULT_TASKFLOW_ID;
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
        String oldTaskFlowId = this.taskFlowId;
        this.taskFlowId = taskFlowId;
        _propertyChangeSupport.firePropertyChange("taskFlowId", oldTaskFlowId, taskFlowId);
    }

    public String getTaskFlowId() {
        return taskFlowId;
    }
    
    
    public String defaultTF() {
        setDynamicTaskFlowId(DEFAULT_TASKFLOW_ID);
        return null;
    }
}
