package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Wrappers.BetterServo;

public abstract class IServoModule{

    public boolean ENABLE=true;
    State state;


    public double maxVelocity , acceleration , deceleration;

    public BetterServo[] servos;

    public void setServos(BetterServo... servos)
    {
        this.servos=servos;
    }

    public StateArray states=new StateArray();


    public void setProfileCoefficients()
    {
        for(int i=0;i<servos.length;i++)
            servos[i].setProfileCoefficients(maxVelocity , acceleration , deceleration);
    }

    public abstract void setStates();
    public abstract void updateStatesPosition();
    public abstract void atStart() ;


    private void updateState()
    {
         for(int i=0;i<servos.length ; i++)
             if(!servos[i].inPosition())return;

         state=state.nextState;
    }


    public void setState(String name)
    {
        state=states.get(name);
    }


    private void updateHardware()
    {
        for(int i=0;i<servos.length;i++) {
            servos[i].update();
            if(servos[i].position!=state.getPosition(i))servos[i].setPosition(state.getPosition(i));
        }
    }

    public void telemetry(Telemetry telemetry)
    {
        for(int i=0;i<servos.length;i++)
        {
            telemetry.addData("RampServo" , servos[i].getPosition());
        }
        telemetry.addData("RampState" , state.name);
    }



    public void update()
    {
        updateStatesPosition();
        updateState();
        updateHardware();
    }



}
