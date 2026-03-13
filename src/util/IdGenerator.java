package util;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {

    private IdGenerator() {}

    private static final Map<Class<?>, Long> idGenerationMap = new HashMap<>();

    public static Long getIdForClass(Class<?> clazz) {
        return idGenerationMap.compute(clazz, (_, v) -> v == null ? 1 : ++v);
    }
}
