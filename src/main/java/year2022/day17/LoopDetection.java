package year2022.day17;

import java.util.HashMap;
import java.util.Map;

public class LoopDetection {
    Map<Object, Object> map = new HashMap<>();

    public Object detectLoop(Object newObject) {

        if (map.containsKey(newObject)) {
            return map.get(newObject);
        }
        return null;
    }

    public void addItem(Object newObject) {
        map.put(newObject, newObject);
    }

}
