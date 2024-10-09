package org.firstinspires.ftc.teamcode.Modules.Intake;

import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.IServoModule;
import org.firstinspires.ftc.teamcode.Wrappers.BetterServo;
import org.opencv.features2d.MSER;

public class Ramp extends IServoModule {

    public static boolean rightServoReversed=false;

    public static double upPosition=0.4 , downPosition=0.6;

    public static double MaxVelocoty=20 , Acceleration=32  , Deceleration=32;

    public Ramp()
    {
        moduleName="RAMP";
        setServos(
                new BetterServo("Servo" , Hardware.sch1 , BetterServo.RunMode.PROFILE , rightServoReversed)
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
        states.addState("up" , upPosition);
        states.addState("down" , downPosition);

        states.addState("goingUp" , states.get("up"), upPosition);
        states.addState("goingDown",  states.get("down"), downPosition);


    }

    @Override
    public void updateStatesPosition(){
        states.get("up").updatePositions(upPosition);
        states.get("down").updatePositions(downPosition);

        states.get("goingUp").updatePositions(upPosition);
        states.get("goingDown").updatePositions(downPosition);
    }

    @Override
    public void atStart()  {
        state=states.get("up");
    }

}
