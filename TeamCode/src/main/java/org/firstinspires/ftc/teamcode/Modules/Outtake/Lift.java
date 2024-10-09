package org.firstinspires.ftc.teamcode.Modules.Outtake;

import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Wrappers.BetterMotor;
import org.firstinspires.ftc.teamcode.Wrappers.Encoder;

public class Lift {


    enum State{
        UP() , GOING_UP(UP) , DOWN() , GOING_DOWN(DOWN);
        State nextState;
        State(State nextState)
        {
            this.nextState=nextState;
        }
        State()
        {
            this.nextState=this;
        }
    }
    State state=State.DOWN;
    BetterMotor motorLeft , motorRight;
    Encoder encoder;
    public static boolean motorLeftReversed=false;
    public static int maxPosition=1200;

    public static double kP , kI , kD;


    double position;
    public Lift()
    {

        motorLeft=new BetterMotor(Hardware.mch0 , BetterMotor.RunMode.PID , motorLeftReversed);
        motorRight=new BetterMotor(Hardware.mch0 , BetterMotor.RunMode.PID , motorLeftReversed);

        motorLeft.setPidCoefficients(kP , kD , kI);
        motorRight.setPidCoefficients(kP , kI , kD);
        encoder=new Encoder(Hardware.mch0 , false);

    }


    public void goUp()
    {
        state=State.GOING_UP;
    }
    public void goDown()
    {
        if(state!=State.GOING_DOWN && state!=State.DOWN)
            state=State.GOING_DOWN;
    }
    public void setPosition(int position)
    {
        this.position=position;
        this.position=Math.min(maxPosition , position);
    }
    public void increasePosition(int extra)
    {
        position+=extra;
        position=Math.min(maxPosition , position);
    }

    public boolean inPosition()
    {
        if(Math.abs(position- encoder.getPosition())<30)return true;
        return false;
    }
    public void decreasePosition(int extra)
    {
        position-=extra;
        position=Math.max(150 , position);
    }
    private void updateState()
    {
        switch(state)
        {
            case GOING_UP:
                if(Math.abs(position- encoder.getPosition())<30)state=state.nextState;
                break;
            case UP:
            case DOWN:
                break;
            case GOING_DOWN:
                if(motorLeft.getVelocity()<0.05)state=state.nextState;
                break;
        }
    }
    private void updateHardware()
    {
        switch(state)
        {
            case GOING_UP:
            case UP:
                motorLeft.setPosition(position);
                motorRight.setPosition(position);
                break;
            case DOWN:
                motorLeft.setPower(-0.05);
                motorRight.setPower(-0.05);
                break;
            case GOING_DOWN:
                motorLeft.setPower(-0.9);
                motorRight.setPower(-0.9);
                break;
        }
    }

    private void updateCoefficient()
    {
        motorLeft.setPidCoefficients(kP , kD , kI);
        motorRight.setPidCoefficients(kP , kI , kD);
    }

    public void update()
    {
        updateCoefficient();
        updateState();
        updateHardware();
    }


}
