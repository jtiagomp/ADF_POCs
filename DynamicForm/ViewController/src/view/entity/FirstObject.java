package view.entity;

import java.util.List;

import javax.faces.model.SelectItem;

public class FirstObject {
    
    private Integer id;
    private List<SelectItem> headerLOV;
    private List<SelectItem> parameterLOV;

    
    public FirstObject() {
        super();
    }

    public FirstObject(Integer id, List<SelectItem> headerLOV, List<SelectItem> parameterLOV) {
        this.id = id;
        this.headerLOV = headerLOV;
        this.parameterLOV = parameterLOV;
    }

    public void setHeaderLOV(List<SelectItem> headerLOV) {
        this.headerLOV = headerLOV;
    }

    public List<SelectItem> getHeaderLOV() {
        return headerLOV;
    }

    public void setParameterLOV(List<SelectItem> parameterLOV) {
        this.parameterLOV = parameterLOV;
    }

    public List<SelectItem> getParameterLOV() {
        return parameterLOV;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
