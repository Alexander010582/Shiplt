public class StandardParcel extends Parcel{ // стандартная посылка


    private static final int BASE_RATE = 2; // Базовая стоимость для стандартной посылки

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    protected int getBaseRate() {
        return BASE_RATE;
    }

}