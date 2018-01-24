package view.mb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import view.entity.FirstObject;

import view.utils.ADFUtils;

public class FirstFormMB {

    private List<FirstObject> dynForm = new ArrayList<FirstObject>(); ;
    private RichPanelGroupLayout pgLFormBinding;

    public FirstFormMB() {
        super();
    }


    public void setDynForm(List<FirstObject> dynForm) {
        this.dynForm = dynForm;
    }

    public List<FirstObject> getDynForm() {
        return dynForm;
    }

    public void entryMethodCall() {
        List<SelectItem> headerLOV = new ArrayList<>();
        SelectItem headerItem = new SelectItem();
        headerItem.setLabel("HeaderLabel1");
        headerItem.setValue("headerValue1");
        headerLOV.add(headerItem);
        List<SelectItem> parameterLOV = new ArrayList<>();
        SelectItem paramItem = new SelectItem();
        paramItem.setLabel("ParamLabel1");
        paramItem.setValue("ParamValue1");
        parameterLOV.add(paramItem);

        FirstObject object = new FirstObject(1, headerLOV, parameterLOV);

        getDynForm().add(object);
    }

    public void addElementAL(ActionEvent actionEvent) {
        int randomNum = ThreadLocalRandom.current().nextInt(2, 23122 + 1);
        List<SelectItem> headerLOV = new ArrayList<>();
        SelectItem headerItem = new SelectItem();
        headerItem.setLabel("HeaderLabel" + randomNum);
        headerItem.setValue("headerValue" + randomNum);
        headerLOV.add(headerItem);
        List<SelectItem> parameterLOV = new ArrayList<>();
        SelectItem paramItem = new SelectItem();
        paramItem.setLabel("ParamLabel" + randomNum);
        paramItem.setValue("ParamValue" + randomNum);
        parameterLOV.add(paramItem);

        FirstObject object = new FirstObject(randomNum, headerLOV, parameterLOV);
        getDynForm().add(object);
    }

    public void delElementAL(ActionEvent actionEvent) {
        FirstObject index = (FirstObject) actionEvent.getComponent()
                                             .getAttributes()
                                             .get("index");

        if (getDynForm().size() > 1) {
            getDynForm().remove(index);
        } else {
            ADFUtils.showFacesMessage(FacesMessage.SEVERITY_WARN, "Last item!");
        }
    }

    public void setPgLFormBinding(RichPanelGroupLayout pgLFormBinding) {
        this.pgLFormBinding = pgLFormBinding;
    }

    public RichPanelGroupLayout getPgLFormBinding() {
        return pgLFormBinding;
    }
}
