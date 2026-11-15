import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PerishableParcelExpirationTest {

    @Test
    void notExpired() {
        PerishableParcel p = new PerishableParcel("Сыр", 2, "адрес", 10, 5);
        assertFalse(p.isExpired(14), "Срок не истёк: 14 <= 15");
    }

    @Test
    void expired() {
        PerishableParcel p = new PerishableParcel("Молоко", 1, "адрес", 10, 5);
        assertTrue(p.isExpired(16), "Срок истёк: 16 > 15");
    }

    @Test
    void exactlyOnExpirationDay() {
        PerishableParcel p = new PerishableParcel("Йогурт", 1, "адрес", 10, 5);
        assertFalse(p.isExpired(15), "В день истечения срока ещё не считается просроченным");
    }

}