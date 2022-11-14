package plus.jdk.zeus.common.annotation;

import org.springframework.context.annotation.Import;
import plus.jdk.zeus.common.selector.ZeusCommonSelector;
import plus.jdk.zeus.common.selector.ZeusGrpcAuthenticateSelector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import(ZeusCommonSelector.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableCommonSelector {
}
