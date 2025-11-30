package util;

import persistence.AbstractPersistable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class contains static methods that function as utilities for Persistence and
 * Serialization/Deserialization.
 */
public class SaveUtil {

    /**
     * Returns a CSV-ready list deliminated by | (pipe) character.
     * @param list the list of unique objects to list-ify.
     * @return well, the list.
     */
    public static String fastList(List<? extends AbstractPersistable> list) {
        if  (list == null || list.isEmpty()) {
            return "null";
        }

        AbstractPersistable[] objs = list.toArray(new AbstractPersistable[0]);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < objs.length; i++) {
            if (objs[i] == null)
                continue;
            sb.append(objs[i].getUUID());
            if(i < objs.length - 1)
                sb.append("|");
        }

        return sb.toString();
    }

    /**
     * Utility for querying whether a CSV-ready list (such as the ones created
     * by the method above this one) exists in storage. Automatically extracts
     * the ID and queries the list you give it.
     * @param line the CSV-ready list of IDs to search through
     * @param lookup the list to sift through
     * @return a list of elements that were found in persistence store
     * @param <T> a type that can be serialized
     */
    public static <T extends AbstractPersistable> List<T> queryList(String line, LinkedHashMap<String, T> lookup) {
        if (line == null || line.isEmpty() || line.equals("null"))
            return new ArrayList<>();
        String[] tokens = line.split("\\|");
        List<T> list = new ArrayList<>();

        for (String token : tokens) {
            list.add((T) lookup.get(token));
        }

        return list;
    }
}
