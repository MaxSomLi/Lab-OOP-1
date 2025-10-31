package main_j.main;

import main_j.system.Car;
import main_j.system.GasCar;
import main_j.system.Park;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Таксопарк");
        Park park = new Park();
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - додати машину");
        System.out.println("2 - видалити машину");
        System.out.println("3 - обчислити вартість");
        System.out.println("4 - відсортувати за витратами палива");
        System.out.println("5 - знайти машину");
        int choice = scanner.nextInt();
        if (choice == 1) {
            double petrol = 0;
            System.out.println("Назва:");
            String name = scanner.next();
            System.out.println("Тип:");
            String type = scanner.next();
            if (!Objects.equals(type, "Electric") && !Objects.equals(type, "Gas")) {
                System.out.println("Невідомий тип.");
                return;
            }
            if (Objects.equals(type, "Gas")) {
                System.out.println("Бензин:");
                petrol = scanner.nextDouble();
            }
            System.out.println("Швидкість:");
            double speed = scanner.nextDouble();
            System.out.println("Вартість:");
            double price = scanner.nextDouble();
            if (type.equals("Electric") || petrol > 0) {
                if (type.equals("Gas")) {
                    park.addGasCar(new GasCar(-1, name, type, petrol, speed, price));
                } else {
                    park.addCar(new Car(-1, name, type, speed, price));
                }
            }
        } else if (choice == 2) {
            System.out.println("Номер:");
            int idx = scanner.nextInt();
            park.removeCar(idx);
        } else if (choice == 3) {
            park.totalPrice();
        } else if (choice == 4) {
            park.sortByPetrol();
        } else if (choice == 5) {
            System.out.println("Мінімальна:");
            double min = scanner.nextDouble();
            System.out.println("Максимальна:");
            double max = scanner.nextDouble();
            park.findBySpeed(min, max);
        }
    }
}