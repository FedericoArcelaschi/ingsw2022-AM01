package it.polimi.ingsw.communication;

import it.polimi.ingsw.communication.command.Command;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

public class PayCharTest {

    @Test
    void errorMailman() {
        final String com = "paychar mailman 1";
        assertThrowsExactly(IllegalArgumentException.class, ()-> new Command("", com));
        final String com1 = "paychar mailman blue";
        assertThrowsExactly(IllegalArgumentException.class, ()-> new Command("", com));
        final String com2 = "paychar mailman";
        try {
            new Command("", com);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    void JesterErrorStudents() {
        StringBuilder stringBuilder = new StringBuilder("paychar jester");
        final String com = " 1";
        for (int i = 1; i < 8; i++) {
            stringBuilder.append(" blue");
            if(i%2 == 0)
                try {
                    new Command("", stringBuilder.toString());
                } catch (ParseException e) {
                    fail();
                }
            else
                assertThrowsExactly(IllegalArgumentException.class, ()-> new Command("", stringBuilder.toString()));
        }

    }
}
