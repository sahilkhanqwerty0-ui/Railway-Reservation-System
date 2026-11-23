import java.util.*;

class Train {
    int number, seats;
    String name, source, destination, departure;

    Train(int number, String name, String source, String destination, String departure, int seats) {
        this.number = number;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.departure = departure;
        this.seats = seats;
    }

    public String toString() {
        return number + " | " + name + " | " + source + " -> " + destination
                + " | " + departure + " | Seats: " + seats;
    }
}

class Passenger {
    String name, pnr;
    int trainNumber;

    Passenger(String name, String pnr, int trainNumber) {
        this.name = name;
        this.pnr = pnr;
        this.trainNumber = trainNumber;
    }

    public String toString() {
        return "PNR: " + pnr + " | Passenger: " + name + " | Train: " + trainNumber;
    }
}

class RouteGraph {
    private Map<String, List<String>> graph = new HashMap<>();

    void addRoute(String a, String b) {
        graph.putIfAbsent(a, new ArrayList<String>());
        graph.putIfAbsent(b, new ArrayList<String>());
        graph.get(a).add(b);
        graph.get(b).add(a);
    }

    boolean hasRoute(String source, String destination) {
        if (!graph.containsKey(source) || !graph.containsKey(destination)) return false;
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        q.add(source);
        visited.add(source);

        while (!q.isEmpty()) {
            String station = q.poll();
            if (station.equalsIgnoreCase(destination)) return true;
            for (String next : graph.get(station)) {
                if (visited.add(next)) q.add(next);
            }
        }
        return false;
    }
}

class RailwaySystem {
    private List<Train> trains = new ArrayList<>();
    private Map<String, Passenger> bookings = new HashMap<>();
    private Map<Integer, Queue<Passenger>> waitingLists = new HashMap<>();
    private RouteGraph routes = new RouteGraph();
    private Random random = new Random();

    RailwaySystem() {
        routes.addRoute("Bhilai", "Raipur");
        routes.addRoute("Raipur", "Bilaspur");
        routes.addRoute("Bilaspur", "Nagpur");
        routes.addRoute("Raipur", "Durg");

        trains.add(new Train(12856, "Intercity Express", "Bhilai", "Raipur", "08:30", 2));
        trains.add(new Train(18239, "Shivnath Express", "Raipur", "Bilaspur", "10:15", 3));
        trains.add(new Train(12070, "Jan Shatabdi", "Bhilai", "Raipur", "17:45", 1));
    }

    void showTrains() {
        trains.sort(Comparator.comparingInt(t -> t.number));
        System.out.println("\nTRAIN NO | NAME | ROUTE | DEPARTURE | SEATS");
        for (Train t : trains) System.out.println(t);
    }

    void searchTrain(Scanner sc) {
        System.out.print("Source station: ");
        String src = sc.nextLine().trim();
        System.out.print("Destination station: ");
        String dest = sc.nextLine().trim();

        if (!routes.hasRoute(src, dest)) {
            System.out.println("No connected route found.");
            return;
        }

        boolean found = false;
        for (Train t : trains) {
            if (t.source.equalsIgnoreCase(src) && t.destination.equalsIgnoreCase(dest)) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) System.out.println("Route is connected, but no direct train is listed.");
    }

    void bookTicket(Scanner sc) {
        System.out.print("Train number: ");
        int no = readInt(sc);
        Train train = findTrain(no);
        if (train == null) {
            System.out.println("Train not found.");
            return;
        }

        System.out.print("Passenger name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        Passenger p = new Passenger(name, makePNR(), no);
        if (train.seats > 0) {
            train.seats--;
            bookings.put(p.pnr, p);
            System.out.println("Booking CONFIRMED!");
            System.out.println(p);
        } else {
            waitingLists.putIfAbsent(no, new LinkedList<Passenger>());
            waitingLists.get(no).offer(p);
            System.out.println("No seats available. Added to waiting list.");
            System.out.println("Waiting position: " + waitingLists.get(no).size());
            System.out.println("Waiting ID: " + p.pnr);
        }
    }

    void cancelTicket(Scanner sc) {
        System.out.print("Enter PNR: ");
        String pnr = sc.nextLine().trim();
        Passenger p = bookings.remove(pnr);
        if (p == null) {
            System.out.println("Confirmed booking not found. (Waiting-list cancellation is not supported.)");
            return;
        }

        Train t = findTrain(p.trainNumber);
        if (t != null) {
            Queue<Passenger> q = waitingLists.get(p.trainNumber);
            if (q != null && !q.isEmpty()) {
                Passenger next = q.poll();
                bookings.put(next.pnr, next);
                System.out.println("Cancelled. Waiting passenger promoted to CONFIRMED:");
                System.out.println(next);
            } else {
                t.seats++;
                System.out.println("Ticket cancelled. One seat is now available.");
            }
        }
    }

    void checkPNR(Scanner sc) {
        System.out.print("Enter PNR: ");
        String pnr = sc.nextLine().trim();
        Passenger p = bookings.get(pnr);
        if (p != null) {
            System.out.println("Status: CONFIRMED");
            System.out.println(p);
            return;
        }
        for (Queue<Passenger> q : waitingLists.values()) {
            for (Passenger w : q) {
                if (w.pnr.equals(pnr)) {
                    System.out.println("Status: WAITING");
                    System.out.println(w);
                    return;
                }
            }
        }
        System.out.println("PNR not found.");
    }

    private Train findTrain(int no) {
        for (Train t : trains) if (t.number == no) return t;
        return null;
    }

    private String makePNR() {
        String pnr;
        do {
            pnr = String.valueOf(1000000000L + (Math.abs(random.nextLong()) % 9000000000L));
        } while (bookings.containsKey(pnr));
        return pnr;
    }

    private int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.nextLine();
        }
        int n = sc.nextInt();
        sc.nextLine();
        return n;
    }
}

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
