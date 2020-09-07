import Atom.Classloader.SystemURLClassLoader;
import Atom.Test.Intercept;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import java.lang.reflect.InvocationTargetException;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.junit.Assert.assertThat;

public class Test {

    @org.junit.Test
    public void ByteBuddy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Intercept a = new Intercept();
        System.out.println(a.neat("first"));
        new ByteBuddy()
                .redefine(Intercept.class)
                .method(named("neat"))
                .intercept(FixedValue.value("noob"))
                .make()
                .load(SystemURLClassLoader.getURLSystemCl()).getBytes();

        Intercept b = new Intercept();
        System.out.println(a.neat("second"));
    }
}
