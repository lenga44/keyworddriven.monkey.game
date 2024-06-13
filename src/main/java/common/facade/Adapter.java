package common.facade;

import com.google.common.reflect.ClassPath;
import common.keywords.app.action.*;
import common.keywords.app.variable.*;
import common.keywords.app.verify.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter {
    public static Map<Class<?>,Method[]> callClass() throws IOException {
        Map<Class<?>,Method[]> map = new HashMap<>();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> testsClasses = new ArrayList<>();
        for (ClassPath.ClassInfo info: ClassPath.from(loader).getTopLevelClasses()){
            if(info.getName().startsWith("keywords.app.")&& !info.getName().contains("KeyWord"))
                map.put(info.getClass(),info.getClass().getMethods());
        }
        return map;
    }
}
