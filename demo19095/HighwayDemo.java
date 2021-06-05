package demo19095;
import base.Highway;
import base.Truck;
import java.util.ArrayList;

public class HighwayDemo extends Highway{
    HighwayDemo(){
        super();
    }
    public synchronized boolean hasCapacity(){
        if(trucks.size() >= getCapacity()){
            return false;
        }
        else return true;
    }
    public synchronized boolean add(Truck truck){
        if(hasCapacity()){
            trucks.add(truck);
            return true;
        }
        else return false;
    }
    public synchronized void remove(Truck truck){
        trucks.remove(truck);
    }
    private ArrayList<Truck> trucks = new ArrayList<Truck>();

}
