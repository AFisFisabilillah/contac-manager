package fizu.contac.management.validation.annotation;

import fizu.contac.management.validation.constraint.UniqueConstraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueConstraint.class)
public @interface Unique {
    String message() default "field sudah sudah digunakann";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends JpaRepository> repository();

    String fieldName();
}
