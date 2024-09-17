package org.firstinspires.ftc.teamcode.Modules.Intake;

import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.IServoModule;
import org.firstinspires.ftc.teamcode.Wrappers.BetterServo;
import org.opencv.features2d.MSER;

public class Ramp extends IServoModule {

    public static boolean leftServoReversed=false;
    public static boolean rightServoReversed=false;

    public static double initPosition1=0 , initPosition2=0 , upPosition1=0.4 , upPosition2=0.4 , downPosition1=1 , downPosition2=1;

    public static double MaxVelocoty=20 , Acceleration=32  , Deceleration=32;

    public Ramp()
    {

        setServos(
                new BetterServo(Hardware.sch0 , BetterServo.RunMode.PROFILE  , leftServoReversed) ,
                new BetterServo(Hardware.sch1 , BetterServo.RunMode.PROFILE , rightServoReversed)
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
        states.addState("init", initPosition1 , initPosition2);
        states.addState("up" , upPosition1 , upPosition2);
        states.addState("down" , downPosition1 , downPosition2);

        states.addState("goingUp" , states.get("up"), upPosition1 , upPosition2);
        states.addState("goingDown",  states.get("down"), downPosition1 , downPosition2);


    }

    @Override
    public void updateStatesPosition(){
        states.get("init").updatePositions( initPosition1 , initPosition2);
        states.get("up").updatePositions(upPosition1 , upPosition2);
        states.get("down").updatePositions( downPosition1 , downPosition2);

        states.get("goingUp").updatePositions(upPosition1 , upPosition2);
        states.get("goingDown").updatePositions( downPosition1 , downPosition2);
    }

    @Override
    public void atStart()  {
        setState("init");
    }


}
