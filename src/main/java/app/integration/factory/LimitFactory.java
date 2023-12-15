package app.integration.factory;

import org.springframework.stereotype.Component;
import app.integration.models.enumiration.ExpenseCategoryDto;
import app.integration.models.entity.GoodLimit;
import app.integration.models.entity.Limit;
import app.integration.models.entity.ServiceLimit;

@Component
public class LimitFactory {
    public Limit createInstance(String type){
        if (type.equals(ExpenseCategoryDto.SERVICE.name())){
            return new ServiceLimit();
        } else if (type.equals(ExpenseCategoryDto.GOOD.name())) {
            return new GoodLimit();
        }else throw new RuntimeException("invalid Type");
    }
}
