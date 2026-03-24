    // FILE HANDLING
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

    //  DIJKSTRA 
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
