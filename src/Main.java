import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RailwaySystem system = new RailwaySystem();

        while (true) {
            System.out.println("\n===== RAILWAY RESERVATION SYSTEM =====");
            System.out.println("1. View Trains");
            System.out.println("2. Search Train / Route");
            System.out.println("3. Book Ticket");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. PNR Enquiry");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            while (!sc.hasNextInt()) {
                System.out.print("Enter a valid option: ");
                sc.nextLine();
            }
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: system.showTrains(); break;
                case 2: system.searchTrain(sc); break;
                case 3: system.bookTicket(sc); break;
                case 4: system.cancelTicket(sc); break;
                case 5: system.checkPNR(sc); break;
                case 6:
                    System.out.println("Thank you for using the system!");
                    sc.close();
                    return;
                default: System.out.println("Invalid option.");
            }
        }
    }
}
