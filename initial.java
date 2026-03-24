import java.io.*;
import java.util.*;

// ---------------- ROOM CLASS ----------------
class Room {
    int id;
    String location;
    double price;
    double rating;
    boolean available;
    int distance;

    Room(int id, String location, double price, double rating, boolean available) {
        this.id = id;
        this.location = location;
        this.price = price;
        this.rating = rating;
        this.available = available;
        this.distance = Integer.MAX_VALUE;
    }
}

// ---------------- USER CLASS ----------------
class User {
    String username, password, role;

    User(String u, String p, String r) {
        username = u;
        password = p;
        role = r;
    }
}

// ---------------- GRAPH CLASS ----------------
class Graph {
    static class Pair {
        String node;
        int weight;

        Pair(String n, int w) {
            node = n;
            weight = w;
        }
    }

    HashMap<String, List<Pair>> adj = new HashMap<>();

    void addEdge(String u, String v, int w) {
        adj.putIfAbsent(u, new ArrayList<>());
        adj.putIfAbsent(v, new ArrayList<>());
        adj.get(u).add(new Pair(v, w));
        adj.get(v).add(new Pair(u, w));
    }
}

// ---------------- MAIN CLASS ----------------
public class RoomRadar {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);


    
    // ---------------- ADMIN ----------------
    static void adminPanel() {
        while (true) {
            System.out.println("\n--- ADMIN PANEL ---");
            System.out.println("1. Add Room\n2. View Rooms\n3. Delete Room\n4. Logout");

            int ch = sc.nextInt();

            if (ch == 1) {
                System.out.print("ID: "); int id = sc.nextInt();
                System.out.print("Location: "); String loc = sc.next();
                System.out.print("Price: "); double price = sc.nextDouble();
                System.out.print("Rating: "); double rating = sc.nextDouble();

                rooms.add(new Room(id, loc, price, rating, true));
                saveRooms();
                System.out.println("Room added!");

            } else if (ch == 2) {
                displayRooms(rooms);

            } else if (ch == 3) {
                System.out.print("Enter ID: ");
                int id = sc.nextInt();
                rooms.removeIf(r -> r.id == id);
                saveRooms();
                System.out.println("Deleted!");

            } else break;
        }
    }

    // ---------------- USER ----------------
    static void userPanel(Graph g) {
        while (true) {
            System.out.println("\n--- USER PANEL ---");
            System.out.println("1. Search Room\n2. Book Room\n3. Logout");

            int ch = sc.nextInt();

            if (ch == 1) searchRooms(g);
            else if (ch == 2) bookRoom();
            else break;
        }
    }

    // ---------------- SEARCH ----------------
    static void searchRooms(Graph g) {
        System.out.print("Enter your location: ");
        String userLoc = sc.next();

        System.out.print("Enter budget: ");
        double budget = sc.nextDouble();

        HashMap<String, Integer> distMap = dijkstra(g, userLoc);

        ArrayList<Room> filtered = new ArrayList<>();

        for (Room r : rooms) {
            if (r.price <= budget && r.available) {
                r.distance = distMap.getOrDefault(r.location, 999);
                filtered.add(r);
            }
        }

        Collections.sort(filtered, (a, b) -> {
            if (a.distance != b.distance)
                return a.distance - b.distance;
            return Double.compare(a.price, b.price);
        });

        displayRooms(filtered);
    }

    // ---------------- BOOK ----------------
    static void bookRoom() {
        System.out.print("Enter Room ID: ");
        int id = sc.nextInt();

        for (Room r : rooms) {
            if (r.id == id && r.available) {
                r.available = false;
                saveRooms();
                System.out.println("Booked!");
                return;
            }
        }
        System.out.println("Not available!");
    }

    // ---------------- DISPLAY ----------------
    static void displayRooms(ArrayList<Room> list) {
        System.out.println("\nID  Location  Price  Dist  Rating  Avail");
        for (Room r : list) {
            System.out.println(r.id + "  " + r.location + "  " +
                    r.price + "  " + r.distance + "km  " +
                    r.rating + "  " + r.available);
        }
    }
