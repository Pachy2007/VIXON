package org.firstinspires.ftc.teamcode.Modules.Intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Wrappers.BetterMotor;
import org.firstinspires.ftc.teamcode.Wrappers.Encoder;

public class Extendo {

    enum State{
        IN , GOING_IN(IN) , OUT(GOING_IN);

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
    State state;


    Encoder encoder;
    BetterMotor motor1;
    public static boolean motor1Reversed=false, encoderReversed=false;
    public static double kP , kI , kD;

    private static double inPower=-0.05 , goingInPower=-1;

    private static double maximExtendoPosition=700;

    double velocity;

    public Extendo()
    {
        motor1=new BetterMotor(Hardware.mch0 , BetterMotor.RunMode.RUN , motor1Reversed);
        encoder=new Encoder(Hardware.mch0 , encoderReversed);

        motor1.setPidCoefficients(kP , kI , kD);

        state=State.OUT;

    }

    public void setVelocity(double velocity)
    {
        this.velocity=velocity;
        state=State.OUT;
    }
    public void setIn()
    {
        state=State.GOING_IN;
    }


    private void updateHardware()
    {
        switch (state)
        {
            case IN:
                motor1.setPower(inPower);
                break;
            case OUT:
                if(encoder.getPosition()<maximExtendoPosition)
                {motor1.setPower(velocity);}
                else {motor1.setPower(0);}
                if( Math.abs(velocity)<0.05 && encoder.getPosition()<100){
                    encoder.resetPosition();
                    state=state.nextState;
                }
                break;
            case GOING_IN:
                motor1.setPower(goingInPower);
                if(motor1.getVelocity()<0.075)state=state.nextState;
                break;
        }
    }

    private void updateCoefficient()
    {
        motor1.setPidCoefficients(kP , kI , kD);
    }



    public void update()
    {
        updateCoefficient();
        updateHardware();
    }









}
