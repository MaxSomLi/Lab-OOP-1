package main_j.system;

import java.io.IOException;
import java.sql.*;

public class GasCar extends Car{

    private double petrol;

    public GasCar(String name, String type, double petrol, double speed, double price, boolean nCar) throws IOException, SQLException {
        super(name, type, speed, price, nCar);
        this.petrol = petrol;
        if (nCar) {
            try {
                Connection c = DriverManager.getConnection(url, user, pass);
                String sql = "INSERT INTO Park.GasCar(Idx, Petrol) VALUES(?, ?);";
                PreparedStatement pstmt = c.prepareStatement(sql);
                pstmt.setInt(1, idx);
                pstmt.setDouble(2, petrol);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public double getPetrol() {
        return petrol;
    }

    @Override
    public void print() {
        System.out.println(name + "--" + petrol + "L--" + speed + "MPH--" + price);
    }
}
