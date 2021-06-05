package demo19095;
import base.*;
public class TruckDemo extends Truck {
    public TruckDemo(){
        super();
        truck_id++;
    }
    @Override
    public Hub getLastHub() {
        return lastHub;
    }
    @Override
    public synchronized void enter(Highway hwy) {
        this.state = "moving";
        this.current_Highway = hwy;
        // hwy.add(this);
        lastHub = hwy.getStart();
        distance_covered = 0;
    }
    @Override
    public String getTruckName(){
        return "Truck19095";
    }
    private double lengthOfHighway(Highway hwy){
        return Math.sqrt(hwy.getStart().getLoc().distSqrd(hwy.getEnd().getLoc()));
    }
    @Override
	protected synchronized void update(int deltaT) {
        current_time += deltaT;
        if(current_time < this.getStartTime()){
            return;
        }
        if(reached){//if already reached don't update
            return;
        }
        if(this.getLoc().getX() == this.getSource().getX() && this.getLoc().getY() == this.getSource().getY()){//if the location truck is same as the source location then find and put it directly on the start hub
            Hub H = Network.getNearestHub(this.getSource());
            this.setLoc(H.getLoc());
            if(H.add(this)){
                state = "halt";
            }
            return;
        }
        if(state.equals("halt")){//if truck is at hub no need to update its cordinates 
            return;
        }
        double lengthOfHighway = Math.sqrt(current_Highway.getStart().getLoc().distSqrd(current_Highway.getEnd().getLoc()));//finding the length of the highway it is currently on

        if(distance_covered > lengthOfHighway || (this.getLoc().getX() == current_Highway.getEnd().getLoc().getX() && this.getLoc().getY() == current_Highway.getEnd().getLoc().getY() )){//If truck covered more distance than it is supposed to cover on that current highway then put it on its endHub 
            this.setLoc(current_Highway.getEnd().getLoc());
            if(current_Highway.getEnd().getLoc().getX() == Network.getNearestHub(this.getDest()).getLoc().getX() && current_Highway.getEnd().getLoc().getY() == Network.getNearestHub(this.getDest()).getLoc().getY()){//if this is the last hub then don't add it in the list of that hub directly put truck on destination
                current_Highway.remove(this);
                state = "halt";
                reached = true;
                setLoc(this.getDest());
                // distance_covered = 0;
                return;
            }
            if(current_Highway.getEnd().add(this)){//if there is space for the truck then add it and remove it from the last highway
                current_Highway.remove(this);
                state = "halt";
                return;
            }
            return;
        }
        cordinateUpdate(deltaT);//update co-ordinates
    }
    private void cordinateUpdate(int deltaT){
        int x1 = current_Highway.getStart().getLoc().getX();
        int y1 = current_Highway.getStart().getLoc().getY();
        int x2 = current_Highway.getEnd().getLoc().getX();
        int y2 = current_Highway.getEnd().getLoc().getY();
        double lengthOfHighway = Math.sqrt(current_Highway.getStart().getLoc().distSqrd(current_Highway.getEnd().getLoc()));
        distance_covered += (current_Highway.getMaxSpeed() * deltaT)/500.0;
        double cosTheta = (x2 - x1)/lengthOfHighway;
        double sinTheta = (y2 - y1)/lengthOfHighway;
        double deltaX = (current_Highway.getMaxSpeed()*deltaT*cosTheta)/500.0;
        double deltaY = (current_Highway.getMaxSpeed()*deltaT*sinTheta)/500.0;
        this.setLoc(new Location(this.getLoc().getX() + (int)Math.round(deltaX), this.getLoc().getY() + (int)Math.round(deltaY)));
    }




    void setCurrentHighway(Highway Hwy){
        this.current_Highway = Hwy;
    }
    private int current_time = 0;//Tracks current time
    private static int truck_id = 0; 
    private Hub lastHub = null;//previous hub of that truck
    private boolean status = false;
    private String state = "halt";//To indicate whether truck is moving or not at hub state = halt on highway  state = moving
    private Highway current_Highway;//To indicate on which highway currently truck is on
    private double distance_covered = 0;//this indicates how much distance it has covered on currentHighway
    private boolean reached = false;//whether it reached its destination
    private boolean isLastHub = false;

}
