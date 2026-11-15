public class FragileParcel extends Parcel implements Trackable{ // хрупкая посылка

    private static final int BASE_RATE = 4;

    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println("Посылка \"" + description + "\" обёрнута в защитную плёнку");
        super.packageItem();
    }

    @Override
    protected int getBaseRate() {
        return BASE_RATE;
    }

}
