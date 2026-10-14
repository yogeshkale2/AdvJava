package Project_JDBC;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class HotelReservationSystem {
    public static void main(String[] args) {
        ReservationDAO dao = new ReservationDAO();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Hotel Reservation System!");

        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Add Reservation");
            System.out.println("2. View Reservations");
            System.out.println("3. Update Reservation");
            System.out.println("4. Delete Reservation");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter Guest Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Room Number: ");
                    int room = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Contact Number: ");
                    String contact = scanner.nextLine();
                    
                    try {
                        System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
                        Date checkIn = Date.valueOf(scanner.nextLine());
                        System.out.print("Enter Check-out Date (YYYY-MM-DD): ");
                        Date checkOut = Date.valueOf(scanner.nextLine());

                        Reservation newRes = new Reservation(room, name, room, contact, checkIn, checkOut);
                        if (dao.addReservation(newRes)) {
                            System.out.println("Reservation added successfully!");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: Invalid date format. Please strictly use YYYY-MM-DD.");
                    }
                    break;

                case 2:
                	List<Reservation> reservations = dao.getAllReservations();

                	System.out.println("\n--- Current Reservations ---");
                	System.out.println("ID | Guest Name | Room | Contact | Check-In | Check-Out");
                	System.out.println("-------------------------------------------------------------------------");
                    
                    for (Reservation res : reservations) {
                        System.out.println(
                            res.getReservationId() + " | " + 
                            res.getGuestName() + " | " + 
                            res.getRoomNumber() + " | " + 
                            res.getContactNumber() + " | " + 
                            res.getCheckInDate() + " | " + 
                            res.getCheckOutDate()
                        );
                    }
                    break;

                case 3:
                    System.out.print("Enter Reservation ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (!dao.reservationExists(updateId)) {
                        System.out.println("Error: Reservation ID not found.");
                        break;
                    }

                    System.out.print("Enter new Guest Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new Room Number: ");
                    int newRoom = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter new Contact Number: ");
                    String newContact = scanner.nextLine();

                    if (dao.updateReservation(updateId, newName, newRoom, newContact)) {
                        System.out.println("Reservation updated successfully!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Reservation ID to cancel: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (!dao.reservationExists(deleteId)) {
                        System.out.println("Error: Reservation ID not found.");
                        break;
                    }

                    if (dao.deleteReservation(deleteId)) {
                        System.out.println("Reservation cancelled successfully.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting System. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}