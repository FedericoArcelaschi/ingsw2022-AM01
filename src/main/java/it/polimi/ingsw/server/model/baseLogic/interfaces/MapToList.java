package it.polimi.ingsw.server.model.baseLogic.interfaces;

import java.util.*;


public interface MapToList {
    /**
     * Interface to transform the dining room into the waiting room.
     *
     * @param map the function argument
     *            all integers must be >= 0;
     * @return the List containing all the 'Key' values for the map repeated foreach time is contained in the map.value
     */
    static <KEY> List<KEY> apply(Map<KEY, Integer> map) {
        List<KEY> keyList = new ArrayList<>();
        Set<KEY> mapKeys = new HashSet<>(map.keySet());
        for (KEY key: mapKeys) {
            int values = map.get(key);
            while(values > 0) {
                keyList.add(key);
                map.put(key, values--);
            }
        }
        return keyList;
    }


}
