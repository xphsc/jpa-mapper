package cn.xphsc.jpamapper.core.lambda;

import cn.xphsc.jpamapper.core.exception.JpaMapperException;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Reflections {
    private static final Pattern GET_PATTERN = Pattern.compile("^get[A-Z].*");
    private static final Pattern IS_PATTERN = Pattern.compile("^is[A-Z].*");

    private Reflections() {
    }

    public static String fieldNameForLambdaFunction(LambdaFunction fn) {
        try {
            Method e = fn.getClass().getDeclaredMethod("writeReplace", new Class[0]);
            e.setAccessible(Boolean.TRUE.booleanValue());
            SerializedLambda serializedLambda = (SerializedLambda)e.invoke(fn, new Object[0]);
            String getter = serializedLambda.getImplMethodName();
            if(GET_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(3);
            } else if(IS_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(2);
            }

            return Introspector.decapitalize(getter);
        } catch (ReflectiveOperationException var) {
            throw new JpaMapperException(var);
        }
    }
    public static List<String> fieldNameForLambdaFunction(LambdaFunction... fn) {
        LinkedList<String> fieldName=new LinkedList<>();
        for(LambdaFunction lambdaFunction:fn){
            fieldName.add(fieldNameForLambdaFunction(lambdaFunction)) ;
        }
        return fieldName;
    }
    public static <T> T classForLambdaSupplier(LambdaSupplier supplier) {
        return (supplier != null ? (T) supplier.get() : null);
    }
}
