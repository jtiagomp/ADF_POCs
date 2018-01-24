package view.utils;
/**
 *  @version $Header: ADFUtils.java Aug 4, 2017.2:28:20 PM Jo�o Tiago Pereira  Exp $
 *  @author  Jo�o Tiago Pereira
 *  @since   release specific (what release of product did this appear in)
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCParameter;
import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;


public class ADFUtils {

    /**
     * Constant for signalling failed SRService checkout during eager test.
     */

    public static final String SRSERVICE_CHECKOUT_FAILED = "SRServiceFailed";


    /**
     * Get application module for an application module data control by name.
     * @param name application module data control name
     * @return ApplicationModule
     */

    public static ApplicationModule getApplicationModuleForDataControl(String name) {

        return (ApplicationModule) JSFUtils.resolveExpression("#{data." + name + ".dataProvider}");

    }

    /**
     * A convenience method for getting the value of a bound attribute in the
     * current page context programatically.
     * @param attributeName of the bound value in the pageDef
     * @return value of the attribute
     */

    public static Object getBoundAttributeValue(String attributeName) {
        return findControlBinding(attributeName).getInputValue();
    }


    /**
     * A convenience method for setting the value of a bound attribute in the
     * context of the current page.
     * @param attributeName of the bound value in the pageDef
     * @param value to set
     */

    public static void setBoundAttributeValue(String attributeName, Object value) {
        findControlBinding(attributeName).setInputValue(value);

    }


    /**

     * Returns the evaluated value of a pageDef parameter.
     * @param pageDefName reference to the page definition file of the page with the parameter
     * @param parameterName name of the pagedef parameter
     * @return evaluated value of the parameter as a String
     */

    public static Object getPageDefParameterValue(String pageDefName, String parameterName) {
        BindingContainer bindings = findBindingContainer(pageDefName);
        DCParameter param = ((DCBindingContainer) bindings).findParameter(parameterName);
        return param.getValue();

    }


    /**

     * Convenience method to find a DCControlBinding as an AttributeBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param bindingContainer binding container
     * @param attributeName name of the attribute binding.
     * @return the control value binding with the name passed in.
     *
     */

    public static AttributeBinding findControlBinding(BindingContainer bindingContainer, String attributeName) {
        if (attributeName != null) {
            if (bindingContainer != null) {
                ControlBinding ctrlBinding = bindingContainer.getControlBinding(attributeName);
                if (ctrlBinding instanceof AttributeBinding) {
                    return (AttributeBinding) ctrlBinding;
                }
            }
        }
        return null;

    }


    /**
     * Convenience method to find a DCControlBinding as a JUCtrlValueBinding
     * to get able to then call getInputValue() or setInputValue() on it.
     * @param attributeName name of the attribute binding.
     * @return the control value binding with the name passed in.
     *
     */

    public static AttributeBinding findControlBinding(String attributeName) {

        return findControlBinding(getBindingContainer(), attributeName);

    }


    /**
     * Return the current page's binding container.
     * @return the current page's binding container
     */
    public static BindingContainer getBindingContainer() {
        return (BindingContainer) JSFUtils.resolveExpression("#{bindings}");

    }


    /**
     * Return the Binding Container as a DCBindingContainer.
     * @return current binding container as a DCBindingContainer
     */
    public static DCBindingContainer getDCBindingContainer() {
        return (DCBindingContainer) getBindingContainer();
    }


    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the value attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsForIterator(String iteratorName, String valueAttrName,
                                                          String displayAttrName) {
        return selectItemsForIterator(findIterator(iteratorName), valueAttrName, displayAttrName);
    }


    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName name of the value attribute to use
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute to use for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */

    public static List<SelectItem> selectItemsForIterator(String iteratorName, String valueAttrName,
                                                          String displayAttrName, String descriptionAttrName) {
        return selectItemsForIterator(findIterator(iteratorName), valueAttrName, displayAttrName, descriptionAttrName);
    }


    /**
     * Get List of attribute values for an iterator.
     * @param iteratorName ADF iterator binding name
     * @param valueAttrName value attribute to use
     * @return List of attribute values for an iterator
     */

    public static List attributeListForIterator(String iteratorName, String valueAttrName) {
        return attributeListForIterator(findIterator(iteratorName), valueAttrName);

    }


    /**
     * Get List of Key objects for rows in an iterator.
     * @param iteratorName iterabot binding name
     * @return List of Key objects for rows
     */
    public static List<Key> keyListForIterator(String iteratorName) {
        return keyListForIterator(findIterator(iteratorName));
    }


    /**
     * Get List of Key objects for rows in an iterator.
     * @param iter iterator binding
     * @return List of Key objects for rows
     */
    public static List<Key> keyListForIterator(DCIteratorBinding iter) {
        List attributeList = new ArrayList();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getKey());
        }
        return attributeList;
    }


    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     * @param iteratorName iterator binding name
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List<Key> keyAttrListForIterator(String iteratorName, String keyAttrName) {
        return keyAttrListForIterator(findIterator(iteratorName), keyAttrName);

    }


    /**
     * Get List of Key objects for rows in an iterator using key attribute.
     *
     * @param iter iterator binding
     * @param keyAttrName name of key attribute to use
     * @return List of Key objects for rows
     */
    public static List<Key> keyAttrListForIterator(DCIteratorBinding iter, String keyAttrName) {
        List attributeList = new ArrayList();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(new Key(new Object[] { r.getAttribute(keyAttrName) }));
        }
        return attributeList;

    }


    /**
     * Get a List of attribute values for an iterator.
     *
     * @param iter iterator binding
     * @param valueAttrName name of value attribute to use
     * @return List of attribute values
     */
    public static List attributeListForIterator(DCIteratorBinding iter, String valueAttrName) {
        List attributeList = new ArrayList();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getAttribute(valueAttrName));
        }
        return attributeList;

    }


    /**
     * Find an iterator binding in the current binding container by name.
     *
     * @param name iterator binding name
     * @return iterator binding
     */
    public static DCIteratorBinding findIterator(String name) {
        DCIteratorBinding iter = getDCBindingContainer().findIteratorBinding(name);
        if (iter == null) {
            throw new RuntimeException("Iterator '" + name + "' not found");

        }

        return iter;

    }


    /**
     * Get List of ADF Faces SelectItem for an iterator binding with description.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of value attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with description
     */

    public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter,

        String valueAttrName,

        String displayAttrName,

        String descriptionAttrName) {

        List<SelectItem> selectItems = new ArrayList<SelectItem>();

        for (Row r : iter.getAllRowsInRange()) {

            selectItems.add(new SelectItem(r.getAttribute(valueAttrName),

                    (String) r.getAttribute(displayAttrName),

                    (String) r.getAttribute(descriptionAttrName)));

        }

        return selectItems;

    }


    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the value of the 'valueAttrName' attribute as the key for
     * the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param valueAttrName name of value attribute to use for key
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */

    public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, String valueAttrName,
                                                          String displayAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();

        for (Row r : iter.getAllRowsInRange()) {

            selectItems.add(new SelectItem(r.getAttribute(valueAttrName),

                    (String) r.getAttribute(displayAttrName)));

        }

        return selectItems;

    }


    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return ADF Faces SelectItem for an iterator binding
     */

    public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, String displayAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName), displayAttrName);

    }


    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     *
     * Uses the rowKey of each row as the SelectItem key
     *
     * @param iteratorName ADF iterator binding name
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */
    public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, String displayAttrName,
                                                               String descriptionAttrName) {
        return selectItemsByKeyForIterator(findIterator(iteratorName), displayAttrName, descriptionAttrName);
    }


    /**
     * Get List of ADF Faces SelectItem for an iterator binding with discription.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @param descriptionAttrName name of the attribute for description
     * @return ADF Faces SelectItem for an iterator binding with discription
     */

    public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, String displayAttrName,
                                                               String descriptionAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(), (String) r.getAttribute(displayAttrName),
                                           (String) r.getAttribute(descriptionAttrName)));
        }
        return selectItems;

    }


    /**
     * Get List of ADF Faces SelectItem for an iterator binding.
     *
     * Uses the rowKey of each row as the SelectItem key.
     *
     * @param iter ADF iterator binding
     * @param displayAttrName name of the attribute from iterator rows to display
     * @return List of ADF Faces SelectItem for an iterator binding
     */
    public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, String displayAttrName) {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Row r : iter.getAllRowsInRange()) {
            selectItems.add(new SelectItem(r.getKey(), (String) r.getAttribute(displayAttrName)));
        }

        return selectItems;

    }


    /**
     * Find the BindingContainer for a page definition by name.
     * Typically used to refer eagerly to page definition parameters. It is
     * not best practice to reference or set bindings in binding containers
     * that are not the one for the current page.
     *
     * @param pageDefName name of the page defintion XML file to use
     * @return BindingContainer ref for the named definition
     */

    private static BindingContainer findBindingContainer(String pageDefName) {
        BindingContext bctx = getDCBindingContainer().getBindingContext();
        BindingContainer foundContainer = bctx.findBindingContainer(pageDefName);
        return foundContainer;
    }


    ////////////////////////////////////////////////ADDED BY JTP ///////////////////////////////////////////////////////////////////////////////


    public static OperationBinding executeBindingOperation(String operationName) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding(operationName);
        operationBinding.execute();
        return operationBinding;
    }

    public static OperationBinding executeBindingOperationWithParams(String operationName,
                                                                     HashMap<String, Object> params) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding(operationName);
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            operationBinding.getParamsMap().put(pair.getKey(), pair.getValue());
            it.remove();
        }
        operationBinding.execute();
        return operationBinding;
    }

    public static void showFacesMessage(FacesMessage.Severity msgType, String message) {
        FacesMessage Message = new FacesMessage(message);
        Message.setSeverity(msgType);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, Message);
    }

    public static Row getCurrentRowBindingIterator(String iterator) {
        DCIteratorBinding iteratorSelected = ADFUtils.getDCBindingContainer().findIteratorBinding(iterator);
        return iteratorSelected.getCurrentRow();
    }


    public static Row[] rowsByIterator(String iter) {
        return findIterator(iter).getAllRowsInRange();
    }

    public static void showPopup(RichPopup popupBinding) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popupBinding.show(hints);
    }

    public static void onTableCustomRowSelection(SelectionEvent selectionEvent) {
        RichTable richTable = (RichTable) selectionEvent.getSource();
        CollectionModel model = (CollectionModel) richTable.getValue();
        JUCtrlHierBinding binding = (JUCtrlHierBinding) model.getWrappedData();
        DCIteratorBinding iteratorBinding = binding.getDCIteratorBinding();
        Object selectRowData = richTable.getSelectedRowData();
        JUCtrlHierNodeBinding node = (JUCtrlHierNodeBinding) selectRowData;
        Key rowKey = node.getRowKey();
        iteratorBinding.setCurrentRowWithKey(rowKey.toStringFormat(true));
    }

    public static void onListViewCustomRowSelection(String iteratorName,
                                                    SelectionEvent selectionEvent) {
        //invoke this EL to set selected row as current row, if it is not invoked then first row will be current row
        invokeEL("#{bindings." + iteratorName + ".collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                 new Object[] { selectionEvent });
    }

    /** * Programmatic invocation of a method that an EL evaluates to. * * @param el EL of the method to invoke * @param paramTypes Array of Class defining the types of the parameters * @param params Array of Object defining the values of the parametrs * @return Object that the method returns */
    public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);
        return exp.invoke(elContext, params);
    }

    /** * Programmatic evaluation of EL. * * @param el EL to evaluate * @return Result of the evaluation */
    public static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        return exp.getValue(elContext);
    }

    public static Object getMBPageFlowScope(String mbName, Class javaBeanClass) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        Application application = fctx.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext context = fctx.getELContext();
        ValueExpression createValueExpression =
            expressionFactory.createValueExpression(context, "#{pageFlowScope." + mbName + "}", javaBeanClass);
        return createValueExpression.getValue(context);
    }

    public static void refreshBindingComponent(UIComponent component) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(component);
    }


    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static UIComponent findComponentInRoot(String pComponentID) {
        UIComponent component = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, pComponentID);
        }
        return component;
    }


    /**
     * Get the specific  component from a root component tree.
     * @param root
     * @param id
     * @return
     */
    public static UIComponent findComponent(UIComponent root, String id) {
        if (id.equals(root.getId()))
            return root;
        UIComponent children = null;
        UIComponent result = null;
        Iterator childrens = root.getFacetsAndChildren();
        while (childrens.hasNext() && (result == null)) {
            children = (UIComponent) childrens.next();
            if (id.equals(children.getId())) {
                result = children;
                break;
            }
            result = findComponent(children, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    public static Locale getLocale() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        UIViewRoot uiRoot = ctx.getViewRoot();
        Locale locale = null;
        if (uiRoot != null) {
            locale = uiRoot.getLocale();
        } else {
            locale = FacesContext.getCurrentInstance()
                                 .getExternalContext()
                                 .getRequestLocale();
        }
        return locale;
    }

//    public static String getResourceProperty(String property) {
//        LogUtils.writeInfoLog("getResourceProperty: " + property + ";  locale " + getLocale());
//
//        String bundle_name = null;
//        if (UserPreferences.english_LOCALE.equals(getLocale().toString())) {
//            bundle_name = "com.bringglobal.adfapp.view.ui.locale";
//        } else {
//            bundle_name = "com.bringglobal.adfapp.view.ui.locale_es";
//        }
//
//        ResourceBundle resourceBundle = BundleFactory.getBundle(bundle_name);
//        String txt = "LB_NOT_FOUND";
//        if (resourceBundle != null) {
//            txt = resourceBundle.getString(property);
//        }
//        return txt;
//    }

    public static Map getSessionScopeMap() {
        ADFContext adfCtx = ADFContext.getCurrent();
        return adfCtx.getSessionScope();
    }

    public static Object getVLCObjectSelected(String iterator, ValueChangeEvent vlc, String attrKeyName) {
        DCIteratorBinding binding = ADFUtils.findIterator(iterator);
        Row[] rows = binding.getAllRowsInRange();
        for (Row row : rows) {
            System.out.println(row.getAttribute(attrKeyName));
            if (row.getAttribute(attrKeyName).equals(vlc.getNewValue())) {
                return row;
            }
        }
        return null;
    }


    public static SecurityContext getSecurityContext() {
        ADFContext adfCtx = ADFContext.getCurrent();
        return adfCtx.getSecurityContext();
    }
}
