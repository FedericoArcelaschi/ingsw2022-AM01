package it.polimi.ingsw.server.model.baseLogic.interfaces;

import org.jetbrains.annotations.Contract;

import java.util.List;

public interface IterableList {
    default String next(List<String> list, String element) {
        int dim = list.size();
        int index = list.indexOf(element);
        if(index == dim - 1)
            return list.get(0);
        else return list.get(index + 1);
    }
}
