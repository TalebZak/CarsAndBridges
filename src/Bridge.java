import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Bridge {
    //semaphore for dir_0 and dir_1 that can hold 3 vehicles
    private Semaphore bridge, mutex;
    //initialize a sephamore array for each direction where every index is a direction
    private Semaphore[] direction;

    private int currentDir;

    private int activeVehicles;

    private int waitingVehicles;

    private int departure_index;

    //constructor
    public Bridge() {
        bridge = new Semaphore(3, true);
        mutex = new Semaphore(1, true);
        direction = new Semaphore[2];
        direction[0] = new Semaphore(0, true);
        direction[1] = new Semaphore(0, true);
        currentDir = 0;
        activeVehicles = 0;
        waitingVehicles = 0;
        departure_index = 0;
    }
    private int reverse_dir(int dir) {
        return dir == 0 ? 1 : 0;
    }
    public void arriveBridge(int dir) {
        try {
            mutex.acquire(1);
            if (currentDir != dir && activeVehicles > 0){
                waitingVehicles++;
                mutex.release(1);
                direction[dir].acquire();
            }
            else{
                currentDir = dir;
                activeVehicles++;
                mutex.release(1);
            }
            bridge.acquire(1);
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

            bridge.release(1);
            mutex.acquire(1);
            if (--activeVehicles == 0){
                while (waitingVehicles > 0){
                    waitingVehicles--;
                    activeVehicles++;
                    currentDir = reverse_dir(dir);
                    direction[reverse_dir(dir)].release();
                }
            }
            mutex.release(1);
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