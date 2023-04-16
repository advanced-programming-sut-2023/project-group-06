package org.example.Model;

public class Resources extends Asset{
    private ResourcesType type;

    public Resources(int amount, ResourcesType type) {
        super(amount);
        this.type = type;
    }

    public ResourcesType getType() {
        return type;
    }
}
