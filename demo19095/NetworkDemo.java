package demo19095;

import java.util.ArrayList;

import base.*;
public class NetworkDemo extends Network {

    @Override
    public void add(Hub hub) {
        Hubs.add(hub);
    }

    @Override
    public void add(Highway hwy) {
        Highways.add(hwy);
        
    }

    @Override
    public void add(Truck truck) {
        Trucks.add(truck);
    }

    @Override
    public void start() {
        for(Truck T : Trucks){
            T.start();
        }
        for(Hub H : Hubs){
            H.start();
        }
    }

    @Override
    public void redisplay(Display disp) {
        for(Hub H : Hubs){
            H.draw(disp);
        }
        for(Highway Hwy : Highways){
            Hwy.draw(disp);
        }
        for(Truck T : Trucks){
            T.draw(disp);
        }  
    }
    @Override
    protected Hub findNearestHubForLoc(Location loc) {
        Hub nearest = Hubs.get(0);//Just in case to avoid returning null
        double distance = 100000;//arbitrary large value
        for(Hub H : Hubs){
            if(Math.sqrt(H.getLoc().distSqrd(loc)) < distance){
                distance = Math.sqrt(H.getLoc().distSqrd(loc));
                nearest = H;
            }
        }
        return nearest;
    }
    private ArrayList<Hub> Hubs = new ArrayList<Hub>();
    private ArrayList<Highway> Highways = new ArrayList<Highway>();
    private ArrayList<Truck> Trucks = new ArrayList<Truck>();

}
