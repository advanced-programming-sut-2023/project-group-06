package org.example.Model.BuildingGroups;

public enum TreeType {
    DESERT_TREE("desert tree"),
    CHERRY_TREE("cherry tree"),
    OLIVE_TREE("olive tree"),
    COCONUT_TREE("coconut tree"),
    DATE_TREE("date tree"),
    ;

    String name;

    TreeType(String name) {
        this.name = name;
    }

    public static TreeType getTreeTypeByString(String type){
        TreeType treeType = null;
        for(TreeType ts : TreeType.values()) if (ts.name.equals(type)) treeType = ts;
        return treeType;
    }
}
