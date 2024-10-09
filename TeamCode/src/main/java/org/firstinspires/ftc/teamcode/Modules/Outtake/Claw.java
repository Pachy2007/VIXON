package org.firstinspires.ftc.teamcode.Modules.Outtake;

import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.IServoModule;
import org.firstinspires.ftc.teamcode.Robot.State;
import org.firstinspires.ftc.teamcode.Wrappers.BetterServo;

public class Claw extends IServoModule {

    public static boolean rightServoReversed=false;

    public static double closePosition=0 , openPosition=1;

    public static double MaxVelocoty=20 , Acceleration=32  , Deceleration=32;

    public Claw()
    {
        moduleName="CLAW";
        setServos(
                new BetterServo("Servo" , Hardware.sch0 , BetterServo.RunMode.PROFILE , rightServoReversed)
        );
        this.maxVelocity=MaxVelocoty;
        this.acceleration=Acceleration;
        this.deceleration=Deceleration;
        setProfileCoefficients();
        setStates();
        atStart();

    }

    @Override
    public void setStates()  {
        states.addState("close" , closePosition);
        states.addState("open"  ,openPosition);

        states.addState("goOpen" , states.get("open"), openPosition);
        states.addState("goClose",  states.get("close"), closePosition);
    }

    @Override
    public void updateStatesPosition(){
        states.get("close").updatePositions(closePosition);
        states.get("open").updatePositions(openPosition);

        states.get("goOpen").updatePositions(openPosition);
        states.get("goClose").updatePositions(closePosition);
    }

    @Override
    public void atStart()  {
        state=states.get("close");
    }
}
