
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
