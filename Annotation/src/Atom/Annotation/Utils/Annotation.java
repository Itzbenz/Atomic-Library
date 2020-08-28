package Atom.Annotation.Utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotation {
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface BuilderProperty {
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Manifest {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Replicate {
        String source() default "";
        String[] target() default "";
        String clazz() default "";

    }
}
