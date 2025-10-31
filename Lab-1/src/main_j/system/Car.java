package main_j.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class Car {
    final String url = "jdbc:mysql://localhost:7845", user = "lisav", pass = Files.readString(Paths.get(".idea/dataSources/pass.txt")).trim();
    String name;
    String type;
    double speed;
    double price;
    int idx;

    public Car(int idx, String name, String type, double speed, double price) throws IOException, SQLException {
        this.idx = idx;
        this.name = name;
        this.type = type;
        this.speed = speed;
        this.price = price;
        if (idx == -1) {
            try {
                Connection c = DriverManager.getConnection(url, user, pass);
                String sql = "INSERT INTO Park.Car(Name, Type, Speed, Price) VALUES(?, ?, ?, ?);", sel = "SELECT * FROM Park.Car;";
                PreparedStatement pstmt = c.prepareStatement(sql), rr = c.prepareStatement(sel);
                pstmt.setString(1, name);
                pstmt.setString(2, type);
                pstmt.setDouble(3, speed);
                pstmt.setDouble(4, price);
                pstmt.executeUpdate();
                ResultSet rs = rr.executeQuery();
                while (rs.next()) {
                    this.idx = rs.getInt("Idx");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int getIdx() {
        return idx;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getSpeed() {
        return speed;
    }

    public double getPrice() {
        return price;
    }

    public void print() {
        System.out.println(name + "--" + type + "--" + speed + "MPH--" + price);
    }
}
