import java.time.LocalDateTime;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import app.integration.models.enumiration.CurrencyTypeDto;
import app.integration.models.enumiration.ExpenseCategoryDto;

public class Test {
    public static Set<CurrencyTypeDto> updateList = Set.of(
            CurrencyTypeDto.KZT,
            CurrencyTypeDto.RUB
    );
    public static void main(String[] args) throws DatatypeConfigurationException {
        System.out.println(toXMLGregorianCalendar(LocalDateTime.now()));
        updateList.forEach(x -> System.out.println(x.name()));
        String expenseCategory = "SERVICE";
        System.out.println(Enum.valueOf(
                ExpenseCategoryDto.class,
                expenseCategory
        ));
    }

    private static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime localDateTime)
            throws DatatypeConfigurationException {
        // Преобразование LocalDateTime в XMLGregorianCalendar
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(
                String.valueOf(localDateTime));
    }
}
