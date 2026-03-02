package yourmod;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static <T, TContainer> T readField(Class<T> type, Class<TContainer> container, TContainer obj, String name) {
        try {
            Field field = container.getDeclaredField(name);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
