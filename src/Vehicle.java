import java.util.concurrent.TimeUnit;
public class Vehicle extends Thread{
    private int vehicle_id;//vehicle id positive integer
    private int dir;//takes 0 or 1 for direction
    private int time_to_cross;//time to cross the bridge
    private Bridge bridge;//bridge object
    public Vehicle(int vehicle_id, int dir, int time_to_cross, Bridge bridge){
        this.vehicle_id = vehicle_id;
        this.dir = dir;
        this.time_to_cross = time_to_cross;
        this.bridge = bridge;
    }
    // implement getters and setters
    public int getVehicle_id() {
        return vehicle_id;
    }

    public int getTime_to_cross() {
        return time_to_cross;
    }
    public Bridge getBridge() {
        return bridge;
    }
    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }
    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
    public int getDir() {
        return dir;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
    @Override
    public void run(){

        bridge.oneVehicle(this.vehicle_id, this.dir, this.time_to_cross);

    }

}
