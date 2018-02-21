package view.dyntreetable.entity;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    
    private String name;
    private EntityType type;
    private String value;
    
    public Entity() {
        super();
    }
    
    private List<Entity> myEntities;


    public Entity(String name, EntityType type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
        myEntities = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public EntityType getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public void setMyEntities(List<Entity> myEntities) {
        this.myEntities = myEntities;
    }

    public List<Entity> getMyEntities() {
        return myEntities;
    }

    public void addentities(Entity entity) {
        myEntities.add(entity);
    }
    
    @Override
    public String toString(){
        return "Name: "  + this.name + "   Type: " + this.type.getName() + "   Value: " + this.value + "; \n        Entities:" + this.myEntities.toString();  
    }
}
