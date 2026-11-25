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
