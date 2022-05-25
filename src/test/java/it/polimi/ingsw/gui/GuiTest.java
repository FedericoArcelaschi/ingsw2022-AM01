package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.ServerMain;
import it.polimi.ingsw.userInterface.gui.Gui;
import it.polimi.ingsw.userInterface.gui.preferencesPane.PreferencePane;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class GuiTest {
    @Test
    public void testPreferences(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerMain s = new ServerMain(1234);
        PreferencePane p = new PreferencePane(10);
        Gui g = new Gui();
        executorService.submit(s);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("lore", s.getConnectedPlayers());
    }
}
