package fizu.contac.management.validation.constraint;


import fizu.contac.management.validation.annotation.Unique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Method;

@Slf4j
public class UniqueConstraint implements ConstraintValidator<Unique, String> {
    private Class<?> repository;
    private String message;
    private String field;
    @Autowired
    private ApplicationContext context;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.repository = constraintAnnotation.repository();
        this.message = constraintAnnotation.message();
        this.field = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try{
            CrudRepository<?,?> crudRepository =  (CrudRepository<?,?>)context.getBean(repository) ;
            String methode  =  "existsBy"+field.substring(0,1).toUpperCase()+field.substring(1);
            Method method = repository.getMethod(methode, field.getClass());

            Boolean result = (Boolean) method.invoke(crudRepository, value);
            log.info(result.toString());
            System.out.println(result.toString());
            if (result) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(message);
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            log.error(e.toString());
            System.out.println(e);
            return false;
        }


    }
}
