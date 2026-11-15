import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ParcelBoxWeightTest {

    @Test
    void addParcelWithinWeightLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10); // лимит 10 кг
        StandardParcel p1 = new StandardParcel("Коробка книг", 3, "адрес1", 1);
        StandardParcel p2 = new StandardParcel("Мелкие вещи", 4, "адрес2", 2);

        box.addParcel(p1);
        box.addParcel(p2);

        List<StandardParcel> contents = box.getAllParcels();
        assertEquals(2, contents.size(), "Обе посылки должны быть добавлены");
        assertEquals(7, contents.stream().mapToInt(Parcel::getWeight).sum(), "Суммарный вес 3+4=7 кг");
    }

    @Test
    void rejectParcelWhenWeightExceeded() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5); // лимит 5 кг
        StandardParcel p1 = new StandardParcel("Тяжёлая коробка", 4, "адрес1", 1);
        StandardParcel p2 = new StandardParcel("Ещё тяжелее", 3, "адрес2", 2); // 4+3=7 > 5

        box.addParcel(p1);
        box.addParcel(p2); // должна быть отклонена

        List<StandardParcel> contents = box.getAllParcels();
        assertEquals(1, contents.size(), "Только первая посылка должна быть добавлена");
        assertEquals(4, contents.get(0).getWeight(), "Должна остаться только первая посылка");
    }

    @Test
    void exactWeightLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(6);
        StandardParcel p1 = new StandardParcel("Ровно 6 кг", 6, "адрес", 1);

        box.addParcel(p1);

        List<StandardParcel> contents = box.getAllParcels();
        assertEquals(1, contents.size(), "Посылка ровно в лимит должна быть добавлена");
    }

}