package ai;

/**
 *
 * @author AMILA
 */


public class QueueNode {

        private int X = -1;             
        private int Y = -1;

        private int PreviousX = -1;     
        private int PreviousY = -1;

        private int State = 0;          //Unknown = 0
                                        //Discovered = 1
                                        //Completed = 2

        private boolean isTarget = false; 
        private boolean isStart = false;   

    public int getPreviousX() {
        return PreviousX;
    }
    
    public void setPreviousX(int PreviousX) {
        this.PreviousX = PreviousX;
    }

    public int getPreviousY() {
        return PreviousY;
    }

    public void setPreviousY(int PreviousY) {
        this.PreviousY = PreviousY;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public boolean getIsTarget() {
        return isTarget;
    }

    public void setIsTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }

    public boolean getIsStart() {
        return isStart;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

}
