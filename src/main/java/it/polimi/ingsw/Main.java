package it.polimi.ingsw;

import it.polimi.ingsw.gui.Gui;

public class Main {
    public static String[] args;
    public static void main(String[] args) {
        Main.args = args;
        Gui gui = new Gui();
        gui.view();
    }
}
