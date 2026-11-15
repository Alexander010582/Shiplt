import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private final List<T> parcels = new ArrayList<>();
    private final int maxWeight;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addParcel(T parcel) {
        int currentTotalWeight = getTotalWeight();
        if (currentTotalWeight + parcel.getWeight() > maxWeight) {
            System.out.println("Не удалось добавить посылку \"" + parcel.getDescription()
                    + "\": превышен максимальный вес коробки (" + maxWeight + " кг).");
            return;
        }
        parcels.add(parcel);
        System.out.println("Посылка \"" + parcel.getDescription() + "\" добавлена в коробку.");
    }

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);// Возвращаем копию, чтобы нельзя было модифицировать внутренний список снаружи
    }

    private int getTotalWeight() {
        int total = 0;
        for (T p : parcels) {
            total += p.getWeight();
        }
        return total;
    }

    public int getCurrentWeight() {
        return getTotalWeight();
    }
}
