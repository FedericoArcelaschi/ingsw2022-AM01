package it.polimi.ingsw.model.expert;

import it.polimi.ingsw.model.*;

import java.util.*;

@FunctionalInterface
public interface InfluenceCharacterExecution { // per il primo studente sar
     boolean execute(int n);
     //Map<Team, Integer> execute(Island island, Castle castle){}
}
