package org.firstinspires.ftc.teamcode.Robot;

public class State {

    String name;

    double[] positions;
    State nextState;

    public State(String name , State nextState , double... positions)
    {
        this.name=name;
        this.nextState=nextState;
        this.positions=positions;
    }
    public State(String name   , double... positions)
    {
        this.name=name;
        this.nextState=this;
        this.positions=positions;
    }

    public double getPosition( int index) {
        return positions[index];
    }

    public boolean verify(String name)
    {
        if(this.name==name)return true;
        return false;
    }


    public void updatePositions(double... positions)
    {
        this.positions=positions;
    }

}
