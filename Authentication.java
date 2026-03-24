
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

