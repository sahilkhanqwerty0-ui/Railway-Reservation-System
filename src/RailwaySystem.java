import java.util.*;

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
