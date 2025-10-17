package main_j.system;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Park {
    private List<Car> cars;
    private List<GasCar> gc;
    final String url = "jdbc:mysql://localhost:7845", user = "lisav", pass = Files.readString(Paths.get(".idea/dataSources/pass.txt")).trim();

    public Park() throws IOException {
        cars = new ArrayList<>();
        gc = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM Park.Car;", gsql = "SELECT * FROM Park.GasCar;";
            PreparedStatement pstmt = c.prepareStatement(sql), gpstmt = c.prepareStatement(gsql);
            ResultSet rs = pstmt.executeQuery(), grs = gpstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String type = rs.getString("Type");
                double speed = rs.getDouble("Speed");
                double price = rs.getDouble("Price");
                Car nc = new Car(name, type, speed, price, false);
                nc.setIdx(rs.getInt("Idx"));
                cars.add(nc);
            }
            while (grs.next()) {
                double petrol = grs.getDouble("Petrol");
                int idx = grs.getInt("Idx");
                cars.stream().filter(o -> o.getIdx() == idx).findFirst().ifPresent(car -> {
                    GasCar g;
                    try {
                        g = new GasCar(car.getName(), car.getType(), petrol, car.getSpeed(), car.getPrice(), false);
                        gc.add(g);
                        g.setIdx(idx);
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addGasCar(GasCar gasCar) {
        gc.add(gasCar);
    }

    public void removeCar(int idx) {
        cars.removeIf(c -> c.getIdx() == idx);
        gc.removeIf(c -> c.getIdx() == idx);
        try {
            Connection c = DriverManager.getConnection(url, user, pass);
            String sql = "DELETE FROM Park.Car WHERE (Idx) = (?);", gsql = "DELETE FROM Park.GasCar WHERE (Idx) = (?);";
            PreparedStatement pstmt = c.prepareStatement(sql), gpstmt = c.prepareStatement(gsql);
            gpstmt.setInt(1, idx);
            gpstmt.executeUpdate();
            pstmt.setInt(1, idx);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void totalPrice() {
        double total = 0;
        for (Car car : cars) {
            total += car.getPrice();
        }
        System.out.println("==" + total);
    }

    public void sortByPetrol() {
        System.out.println("Сортування:");
        gc.sort(Comparator.comparing(GasCar::getPetrol));
        for (GasCar car : gc) {
            car.print();
        }
    }

    public void findBySpeed(double min, double max) {
        System.out.println(min + "--" + max);
        for (Car car : cars) {
            if (car.getSpeed() >= min && car.getSpeed() <= max) {
                car.print();
            }
        }
    }
}
