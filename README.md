# Railway Reservation System (DSA Project)

**Project Number:** 66  
**Student:** Sahil Khan  
**University:** Rungta International Skills University  
**Core Data Structures:** Graph, Queue, HashMap, Sorting  
**Language:** Java

## Overview
A console-based academic project demonstrating how data structures can support a basic railway reservation workflow.

## Features
- View and sort train records by train number.
- Search station connectivity using a Graph and BFS.
- Book tickets when seats are available.
- Add requests to a FIFO waiting list when seats are full.
- Cancel confirmed bookings and promote the next waiting passenger.
- Search booking status by PNR using a HashMap.

## DSA Concepts Used
| Concept | Application |
|---|---|
| Graph | Railway station connectivity and route traversal |
| Queue | Waiting-list processing in FIFO order |
| HashMap | PNR-based booking lookup |
| Sorting | Sorting train records by train number |

## Requirements
- JDK 8 or later
- VS Code / IntelliJ IDEA / Eclipse / any Java IDE

## Run
1. Open this folder in a terminal.
2. Compile:
   ```bash
   javac Main.java
   ```
3. Run:
   ```bash
   java Main
   ```

## Important Notes
- This is an educational console prototype using sample train data.
- The route graph and train list are predefined in the program.
- Data is stored in memory and resets when the program exits.
- Waiting-list cancellation, database storage, payment processing and live railway data are not implemented.

## Future Improvements
- Add database persistence (e.g. MySQL).
- Add a graphical user interface.
- Support waiting-list cancellation and multiple passenger seats.
- Add admin features and train schedule management.
- Integrate an authorized live train-data source.
