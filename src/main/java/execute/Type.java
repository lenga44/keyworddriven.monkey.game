package execute;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Type{
    static final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    private String typeName;
    private String className;

    public Type(String typeName, String className) {
        this.typeName = typeName;
        this.className = className;
    }
    public Class<?> genType() throws IOException {
        Class<?> type = null;
        for (ClassPath.ClassInfo info: ClassPath.from(loader).getTopLevelClasses()) {
            if(info.getPackageName().startsWith("common.keywords."+typeName)){
                if(info.getSimpleName().equals(className)) {
                    type = info.load();
                    break;
                }
            }
        }
        return type;
    }
}
