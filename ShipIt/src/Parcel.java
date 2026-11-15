public abstract class Parcel implements Trackable { // класс посылка

    protected String description; // описание посылки
    protected int weight; // вес посылки
    protected String deliveryAddress; // адрес доставки
    protected int sendDay; // день отправки

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public int getSendDay() {
        return sendDay;
    }


    public void deliver() {
        System.out.println("Посылка \"" + description + "\" доставлена по адресу " + deliveryAddress);
        reportStatus("Доставлено");
    }


    public void packageItem(){
        System.out.println("Посылка упакована \"" + description + "\" упакована");
    }

    protected abstract int getBaseRate();

    public int calculateDeliveryCost() {
        return weight * getBaseRate();
    }

}