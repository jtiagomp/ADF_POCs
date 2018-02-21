package view.dyntreetable.entity;

public enum EntityType {
    
    STRING("java.lang.String","String"),
    DATE("java.lang.Date","Date"),
    COMPLEX(null, null);
     
     
     private String name;
     private String type;
     
     
     
     private EntityType(String name, String type) {
         this.name = name;
         this. type = type;

    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
