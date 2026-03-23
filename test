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

    // ---------------- FILE HANDLING ----------------
    static void loadRooms() {
        try (BufferedReader br = new BufferedReader(new FileReader("rooms.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                rooms.add(new Room(
                        Integer.parseInt(d[0]),
                        d[1],
                        Double.parseDouble(d[2]),
                        Double.parseDouble(d[3]),
                        Boolean.parseBoolean(d[4])
                ));
            }
        } catch (Exception e) {
            System.out.println("No rooms file found.");
        }
    }

    static void saveRooms() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rooms.txt"))) {
            for (Room r : rooms) {
                bw.write(r.id + "," + r.location + "," + r.price + "," + r.rating + "," + r.available);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving rooms.");
        }
    }

    static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                users.add(new User(d[0], d[1], d[2]));
            }
        } catch (Exception e) {
            System.out.println("No users file found.");
        }
    }

    static void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User u : users) {
                bw.write(u.username + "," + u.password + "," + u.role);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving users.");
        }
    }

    // ---------------- DIJKSTRA ----------------
    static HashMap<String, Integer> dijkstra(Graph g, String src) {
        HashMap<String, Integer> dist = new HashMap<>();
        PriorityQueue<Graph.Pair> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.weight));

        pq.add(new Graph.Pair(src, 0));
        dist.put(src, 0);

        while (!pq.isEmpty()) {
            Graph.Pair curr = pq.poll();

            for (Graph.Pair nei : g.adj.getOrDefault(curr.node, new ArrayList<>())) {
                int newDist = curr.weight + nei.weight;

                if (!dist.containsKey(nei.node) || newDist < dist.get(nei.node)) {
                    dist.put(nei.node, newDist);
                    pq.add(new Graph.Pair(nei.node, newDist));
                }
            }
        }
        return dist;
    }

    // ---------------- AUTH ----------------
    static User login() {
        System.out.print("Username: ");
        String u = sc.next();
        System.out.print("Password: ");
        String p = sc.next();

        for (User user : users) {
            if (user.username.equals(u) && user.password.equals(p)) {
                return user;
            }
        }
        System.out.println("Invalid login!");
        return null;
    }

    static void register() {
        System.out.print("New Username: ");
        String u = sc.next();
        System.out.print("Password: ");
        String p = sc.next();

        users.add(new User(u, p, "user"));
        saveUsers();
        System.out.println("Registered!");
    }

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

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        loadRooms();
        loadUsers();

        if (users.isEmpty()) {
            users.add(new User("admin", "1234", "admin"));
            saveUsers();
        }

        // Graph setup
        Graph g = new Graph();
        g.addEdge("Premnagar", "ISBT", 3);
        g.addEdge("Premnagar", "Ballupur", 2);
        g.addEdge("ISBT", "Ballupur", 4);
        g.addEdge("ISBT", "ClockTower", 5);
        g.addEdge("Ballupur", "ClockTower", 3);

        while (true) {
            System.out.println("\n=== ROOMRADAR ===");
            System.out.println("1. Login\n2. Register\n3. Exit");

            int ch = sc.nextInt();

            if (ch == 1) {
                User u = login();
                if (u != null) {
                    if (u.role.equals("admin")) adminPanel();
                    else userPanel(g);
                }
            } else if (ch == 2) register();
            else break;
        }
    }
}
