import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static ParcelBox<StandardParcel> standardBox = new ParcelBox<>(50);      // макс. 50 кг
    private static ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(30);      // макс. 30 кг
    private static ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(40); // макс. 40 кг


    private static List<Trackable> trackableParcels = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    updateTrackingStatus();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Обновить статус посылки");
        System.out.println("5 - Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    // реализуйте методы ниже

    private static void addParcel() {
        System.out.println("--- Добавление посылки ---");
        System.out.print("Описание посылки: ");
        String description = scanner.nextLine();

        System.out.print("Вес (кг, целое число): ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.print("Адрес доставки: ");
        String deliveryAddress = scanner.nextLine();

        System.out.print("День отправки (число месяца): ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        System.out.println("Тип посылки:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая (с трекингом)");
        System.out.println("3 — Скоропортящаяся");
        System.out.print("Введите номер типа: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: нужно ввести число 1, 2 или 3.");
            scanner.nextLine();
            return;
        }
        int typeChoice = scanner.nextInt();
        scanner.nextLine();

        switch (typeChoice) {
            case 1: {
                StandardParcel p = new StandardParcel(description, weight, deliveryAddress, sendDay);
                standardBox.addParcel(p);
                break;
            }
            case 2: {
                FragileParcel p = new FragileParcel(description, weight, deliveryAddress, sendDay);
                fragileBox.addParcel(p);
                trackableParcels.add(p); // отслеживание для хрупких посылок
                break;
            }
            case 3: {
                System.out.print("Срок годности в днях (timeToLive): ");
                int timeToLive = Integer.parseInt(scanner.nextLine());
                if (timeToLive <= 0) {
                    System.out.println("Ошибка: timeToLive должен быть больше 0.");
                    return;
                }
                PerishableParcel p = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
                perishableBox.addParcel(p);
                break;
            }
            default:
                System.out.println("Неверный тип посылки.");
                return;
        }

        System.out.println("Посылка успешно добавлена в соответствующую коробку!");
    }

    private static void sendParcels() {
        List<Parcel> all = new ArrayList<>();
        all.addAll(standardBox.getAllParcels());
        all.addAll(fragileBox.getAllParcels());
        all.addAll(perishableBox.getAllParcels());

        if (all.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }

        System.out.println("\n--- Отправка посылок ---");
        for (Parcel p : all) {
            p.packageItem();
            p.deliver();

            if (p instanceof PerishableParcel) {
                PerishableParcel pp = (PerishableParcel) p;
                int currentDay = pp.getSendDay() + 2; // условный «сегодня»
                if (pp.isExpired(currentDay)) {
                    System.out.println("Посылка \"" + p.getDescription() + "\" могла испортиться!");
                }
            }
            System.out.println();
        }
    }

    private static void calculateCosts() {
        List<Parcel> all = new ArrayList<>();
        all.addAll(standardBox.getAllParcels());
        all.addAll(fragileBox.getAllParcels());
        all.addAll(perishableBox.getAllParcels());

        if (all.isEmpty()) {
            System.out.println("Нет посылок — стоимость 0 руб.");
            return;
        }

        System.out.println("\n--- Расчёт стоимости ---");
        int totalCost = 0;
        for (Parcel p : all) {
            int cost = p.calculateDeliveryCost();
            System.out.println(p.getDescription() + ": " + cost + " руб.");
            totalCost += cost;
        }
        System.out.println("Общая стоимость всех доставок: " + totalCost + " руб.");
    }

    private static void updateTrackingStatus() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет отправлений с трекингом.");
            return;
        }

        System.out.print("Новое местоположение для всех отслеживаемых отправлений: ");
        String newLocation = scanner.nextLine();
        if (newLocation.trim().isEmpty()) {
            System.out.println("Местоположение не введено. Пропущено.");
            return;
        }

        System.out.println("--- Обновление статуса трекинга ---");
        for (Trackable t : trackableParcels) {
            t.reportStatus(newLocation);
        }
        System.out.println();
    }
    private static void showBoxContents() {
        System.out.println("--- Выбор коробки ---");
        System.out.println("1 — Коробка для стандартных посылок");
        System.out.println("2 — Коробка для хрупких посылок");
        System.out.println("3 — Коробка для скоропортящихся посылок");
        System.out.print("Введите номер коробки: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: нужно ввести 1, 2 или 3.");
            scanner.nextLine();
            return;
        }
        int boxChoice = scanner.nextInt();
        scanner.nextLine();

        System.out.println();
        switch (boxChoice) {
            case 1:
                printBoxContents("Стандартная коробка", standardBox.getAllParcels(), standardBox.getCurrentWeight());
                break;
            case 2:
                printBoxContents("Хрупкая коробка", fragileBox.getAllParcels(), fragileBox.getCurrentWeight());
                break;
            case 3:
                printBoxContents("Скоропортящаяся коробка", perishableBox.getAllParcels(), perishableBox.getCurrentWeight());
                break;
            default:
                System.out.println("Неверная коробка.");
        }
        System.out.println();
    }
    private static <T extends Parcel> void printBoxContents(String boxName, List<T> parcels, int currentWeight) {
        System.out.println(boxName + " (текущий вес: " + currentWeight + " кг)");
        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }
        for (T p : parcels) {
            System.out.println("- Посылка: \"" + p.getDescription() + "\", вес: " + p.getWeight() + " кг");
        }
    }

}