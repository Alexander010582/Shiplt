public class PerishableParcel extends Parcel{ // скоропортящаяся посылка

    private static final int BASE_RATE = 3;
    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        if (timeToLive <= 0) {
            throw new IllegalArgumentException("Срок годности должен быть больше 0");
        }
        this.timeToLive = timeToLive;
    }

    @Override
    protected int getBaseRate() {
        return BASE_RATE;
    }

    public boolean isExpired(int currentDay) {
        int expirationDay = sendDay + timeToLive;
        return currentDay > expirationDay;
    }

}
