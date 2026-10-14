package Project_JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/HotelDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; 

    // Method to get DB connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean addReservation(Reservation res) {
        String query = "INSERT INTO reservations (guest_name, room_number, contact_number, check_in_date, check_out_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, res.getGuestName());
            pstmt.setInt(2, res.getRoomNumber());
            pstmt.setString(3, res.getContactNumber());
            pstmt.setDate(4, res.getCheckInDate());
            pstmt.setDate(5, res.getCheckOutDate());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding reservation: " + e.getMessage());
            return false;
        }
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        try (Connection con = getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Reservation(
                    rs.getInt("reservation_id"),
                    rs.getString("guest_name"),
                    rs.getInt("room_number"),
                    rs.getString("contact_number"),
                    rs.getDate("check_in_date"),
                    rs.getDate("check_out_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching reservations: " + e.getMessage());
        }
        return list;
    }

    public boolean reservationExists(int id) {
        String query = "SELECT reservation_id FROM reservations WHERE reservation_id = ?";
        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error checking reservation: " + e.getMessage());
            return false;
        }
    }

    public boolean updateReservation(int id, String name, int room, String contact) {
        String query = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? WHERE reservation_id = ?";
        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, room);
            pstmt.setString(3, contact);
            pstmt.setInt(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating reservation: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteReservation(int id) {
        String query = "DELETE FROM reservations WHERE reservation_id = ?";
        try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
            return false;
        }
    }
}



