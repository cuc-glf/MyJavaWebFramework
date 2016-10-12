package tech.gaolinfeng.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaolf on 16/10/11.
 */
public class CollectionUtil {

    private CollectionUtil() {
        // prevent initialization
    }

    public static Map<String, Object> asMap(String key, Object object) {
        return asMap(new Object[]{key, object});
    }

    public static Map<String, Object> asMap(String key1, Object object1,
                                            String key2, Object object2) {
        return asMap(new Object[][]{
                {key1, object1},
                {key2, object2}
        });
    }

    public static Map<String, Object> asMap(String key1, Object object1,
                                            String key2, Object object2,
                                            String key3, Object object3) {
        return asMap(new Object[][]{
                {key1, object1},
                {key2, object2},
                {key3, object3}
        });
    }

    public static Map<String, Object> asMap(Object[]... objectsArray) {
        Map<String, Object> map = new HashMap<>();
        for (Object[] objects : objectsArray) {
            if (objects.length != 2) {
                throw new AssertionError();
            } else {
                map.put((String) objects[0], objects[1]);
            }
        }
        return map;
    }

}
