import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Bridge {
    //semaphore for dir_0 and dir_1 that can hold 3 vehicles
    private Semaphore sem_dir_0, sem_dir_1, sem_bridge, sem_lock;
    private int car_dir0, car_dir1, car_cnt, car, waiting_dir0, waiting_dir1, waiting_bridge0,
            waiting_bridge1, departure_index, current_dir;

    //constructor
    public Bridge() {
        sem_dir_0 = new Semaphore(3);
        sem_dir_1 = new Semaphore(3);
        sem_bridge = new Semaphore(1);
        sem_lock = new Semaphore(1);
        car_dir0 = 0;
        car_dir1 = 0;
        car_cnt = 0;
        car = 0;
        waiting_dir0 = 0;
        waiting_dir1 = 0;
        waiting_bridge0 = 0;
        waiting_bridge1 = 0;
        departure_index = 0;
        current_dir = -1;
    }


    public void arriveBridge(int dir) {
        try {
            if (dir == 0) {
                if (car_cnt == 0)
                    sem_bridge.acquire(1);
                sem_dir_0.acquire(1);
                car_cnt++;
            } else {
                if (car == 0)
                    sem_bridge.acquire(1);
                sem_dir_1.acquire(1);
                car++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crossBridge(int vehicle_id, int dir, int time_to_cross) {
        try {
            System.out.println("Vehicle " + vehicle_id + " is crossing the bridge from direction " + dir);
            TimeUnit.SECONDS.sleep(time_to_cross);
            //release the bridge
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exitBridge(int vehicle_id, int dir) {
        try {
            System.out.printf("Leaving the bridge: Car %d with departure index %d coming dir %d\n",
                    vehicle_id, ++departure_index, dir);
            if (dir == 0) {
                sem_dir_0.release(1);
                car_cnt--;

                if (car_cnt == 0)
                    sem_bridge.release(1);
            } else {
                sem_dir_1.release(1);
                car--;
                if (car == 0)
                    sem_bridge.release(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void oneVehicle(int vehicle_id, int dir, int time_to_cross) {
        //print the number of the vehicle and the direction it is arriving from
        try {
            System.out.println("Vehicle " + vehicle_id + " is arriving from direction " + dir);
            arriveBridge(dir);
            crossBridge(vehicle_id, dir, time_to_cross);
            exitBridge(vehicle_id, dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}