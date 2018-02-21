package view.dyntreetable.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;

import view.dyntreetable.entity.Entity;
import view.dyntreetable.entity.EntityType;

public class DynTreeTableMB implements Serializable {
    @SuppressWarnings("compatibility:3855919151368662160")
    private static final long serialVersionUID = 1L;

    private ChildPropertyTreeModel dynTreeTable;


    public DynTreeTableMB() {
        List<Entity> entities = new ArrayList<>();

        Entity entity = new Entity("name", EntityType.COMPLEX, "EntitiesType");
        if (EntityType.COMPLEX.equals(entity.getType())) {
            Entity level11 = new Entity("name11", EntityType.STRING, null);
            entity.addentities(level11);
            
            
            Entity level12 = new Entity("name12", EntityType.COMPLEX, "Level12Type");
            Entity level21 = new Entity("name21", EntityType.STRING, null);
            level12.addentities(level21);
            entity.addentities(level12);
            
            
            Entity level13 = new Entity("name13", EntityType.DATE, null);
            entity.addentities(level13);
        }


        entities.add(entity);
        dynTreeTable = new ChildPropertyTreeModel(entities, "myEntities");
    }


    public void setDynTreeTable(ChildPropertyTreeModel dynTreeTable) {
        this.dynTreeTable = dynTreeTable;
    }

    public ChildPropertyTreeModel getDynTreeTable() {
        return dynTreeTable;
    }

    public void SubmitValuesAL(ActionEvent actionEvent) {
        List<Entity> entitiesValues = (List<Entity>) dynTreeTable.getWrappedData();
        for (Entity entity : entitiesValues) {
            System.out.println("ENTITIES = " + entity.toString());
        }
    }
}
