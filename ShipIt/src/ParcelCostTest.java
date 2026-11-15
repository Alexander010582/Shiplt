import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParcelCostTest {

    @Test
    void standardParcelCost() {
        StandardParcel p = new StandardParcel("Книга", 3, "ул. Ленина, 1", 10);
        assertEquals(6, p.calculateDeliveryCost(), "Стандартная посылка: 3 кг * 2 руб = 6 руб");
    }

    @Test
    void fragileParcelCost() {
        FragileParcel p = new FragileParcel("Ваза", 2, "ул. Мира, 5", 12);
        assertEquals(8, p.calculateDeliveryCost(), "Хрупкая посылка: 2 кг * 4 руб = 8 руб");
    }

    @Test
    void perishableParcelCost() {
        PerishableParcel p = new PerishableParcel("Торт", 4, "пр. Победы, 3", 15, 3);
        assertEquals(12, p.calculateDeliveryCost(), "Скоропортящаяся: 4 кг * 3 руб = 12 руб");
    }

}