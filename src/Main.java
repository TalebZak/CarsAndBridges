import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void execute(Vehicle[] vehicles, int delay) throws InterruptedException {

        for (int i = 0; i < vehicles.length; i++) {
            if (i != 0 && i%delay == 0) {
                TimeUnit.SECONDS.sleep(10);
            }
            System.out.println("Vehicle " + vehicles[i].getVehicle_id() + " is arriving with direction " + vehicles[i].getDir());
            vehicles[i].start();
        }
    }
    public static void main(String[] args) {
        Bridge bridge = new Bridge();
        Vehicle[] vehicles = new Vehicle[20];
        /*
        A menu that allows the user to choose from:
         1. Delay after every 5 cars
         2. Delay after every 10 cars
         3. all 20 cars at once*/
        System.out.println("Choose a delay option: ");
        System.out.println("1. Delay after every 5 cars");
        System.out.println("2. Delay after every 10 cars");
        System.out.println("3. All 20 cars at once");
        System.out.println("Choose something: ");
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 20; i++) {
            //assign every vehicle a random direction and random time to cross
            int dir = (int) (Math.random() * 2);
            int time_to_cross = (int) (Math.random() * 5 + 1);
            vehicles[i] = new Vehicle(i+1, dir, time_to_cross, bridge);

        }
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                //call the execute function with a delay of 5
                try {
                    execute(vehicles, 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                //call the execute function with a delay of 10
                try {
                    execute(vehicles, 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                //call the execute function with a delay of 20
                try {
                    execute(vehicles, 20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Invalid option");
                System.exit(0);
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


