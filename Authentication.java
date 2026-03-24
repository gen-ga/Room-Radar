
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
