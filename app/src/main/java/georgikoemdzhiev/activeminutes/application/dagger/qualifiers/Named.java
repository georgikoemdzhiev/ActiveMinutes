package georgikoemdzhiev.activeminutes.application.dagger.qualifiers;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Georgi Koemdzhiev on 14/02/2017.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Named {
    String value() default "";
}
