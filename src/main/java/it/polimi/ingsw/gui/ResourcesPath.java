package it.polimi.ingsw.gui;

public enum ResourcesPath {
    STUDENTS("file:/Users/federicoarcelaschi/Documents/Dev/ingsw2022-AM01/src/main/resources/"),
    CASTLE("file:/Users/federicoarcelaschi/Documents/Dev/ingsw2022-AM01/src/main/resources/castle/castle.png"),
    ISLANDS("file:/Users/federicoarcelaschi/Documents/Dev/ingsw2022-AM01/src/main/resources/islands/");

    public String path;

    ResourcesPath(String path) {
        this.path = path;
    }
}
