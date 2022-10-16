public class Main {
    public static void main(String[] args) {
        Bridge bridge = new Bridge();
        Vehicle[] vehicles = new Vehicle[20];
        for (int i = 0; i < 20; i++) {
            vehicles[i] = new Vehicle(i, i%2, i % 3 + 1, bridge);
            System.out.println("Vehicle " + i + " is arriving with direction " + 0);
            vehicles[i].start();
        }
        //loop on every vehicle and join if it is alive and not null
        for (int i = 0; i < 20; i++) {
            try {
                if (vehicles[i] != null && vehicles[i].isAlive()) {
                    vehicles[i].join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


