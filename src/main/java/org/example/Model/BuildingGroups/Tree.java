package org.example.Model.BuildingGroups;

import org.example.Model.Kingdom;

public class Tree extends Building{
    TreeType treeType;

    public Tree(int xCoordinate, int yCoordinate, TreeType treeType) {
        super(null, BuildingType.TREE, xCoordinate, yCoordinate);
        this.treeType = treeType;
    }

    public TreeType getTreeType() {
        return treeType;
    }
}
