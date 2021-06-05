package demo19095;
import base.Hub;
import java.util.Queue;
import base.Truck;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import base.Highway;
import base.Location;
import base.Network;
import java.util.*;

public class HubDemo extends Hub {
    HubDemo(Location loc){
        super(loc);
        // AllHubs.add(this);
        
    }
    public synchronized boolean add(Truck truck){
        if(q.size() >= this.getCapacity())return false;
        else{
            q.add(truck);
            return true;
        }
    }
    public synchronized void remove(Truck truck){
        q.remove(truck);
    }
    
    boolean DFS(Hub dest,Hub last,Set<Hub> visited){//This function will return true if starting from last can we reach dest and false otherwise
        if(last.equals(dest))return true;
        visited.add(last);
        for(Highway hwy : last.getHighways()){
            Hub H = hwy.getEnd();
            if(visited.contains(H) == false && DFS(dest,H,visited)){//if this node is not visited and if it can lead us to destination node then return true
                return true;
            }
        }
        return false;//This means there is no way found
    }

    public synchronized Highway getNextHighway(Hub last,Hub dest){
        Set<Hub> visited = new HashSet<Hub>();//It will be like maintaining a boolean array for visited so that it does not get stuck in loop or revisit the already visited ones
        for(Highway H : this.getHighways()){//call this function on all the highways at this hub to see which highway can lead us the dest node
            visited.add(this);
            if(DFS(dest,H.getEnd(),visited)){
                return H;

            }
        }
        System.out.println("Danger");
        return null;                

    }
    @Override
    protected synchronized void processQ(int deltaT){
        ArrayList<Truck> removed = new ArrayList<Truck>();
        for(Truck T : q){
            Highway next = getNextHighway(this, Network.getNearestHub(T.getDest()));//for all trucks standing on this hub find if there is any truck which can move further
            if(next.add(T)){//if the next highway has capacity then make it move
                T.enter(next);
                removed.add(T);//we cannot delete while travelling and instead of using iterators we can save to be deleted trucks in separate list and delete them later
            }
        }
        for(Truck T : removed){
            q.remove(T);
        }
        removed.clear();
    }
    // New data members
    private Queue<Truck> q = new LinkedList<Truck>();//It is a queue of trucks standing on a hub
    // private static ArrayList<Hub> AllHubs = new ArrayList<Hub>();
}
