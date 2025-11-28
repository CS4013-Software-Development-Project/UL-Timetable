package util;

import persistence.AbstractPersistable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SaveUtil {

    public static String fastList(List<? extends AbstractPersistable> list) {
        if  (list == null || list.isEmpty()) {
            return " ";
        }

        AbstractPersistable[] objs = list.toArray(new AbstractPersistable[list.size()]);
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

    public static <T extends AbstractPersistable> List<T> queryList(String line, LinkedHashMap<String, T> lookup) {
        String[] tokens = line.split("\\|");
        List<T> list = new ArrayList<>();

        for (String token : tokens) {
            list.add((T) lookup.get(token));
        }

        return list;
    }
}
