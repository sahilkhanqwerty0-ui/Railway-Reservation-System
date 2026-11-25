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
