package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;

public class Tree extends Building{
    TreeType treeType;

    public Tree(Kingdom owner, BuildingType buildingType, int xCoordinate, int yCoordinate, TreeType treeType) {
        super(owner, buildingType, xCoordinate, yCoordinate);
        this.treeType = treeType;
    }

    public TreeType getTreeType() {
        return treeType;
    }
}
