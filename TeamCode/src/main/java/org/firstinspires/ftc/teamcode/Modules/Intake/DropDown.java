package org.firstinspires.ftc.teamcode.Modules.Intake;

import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.IServoModule;
import org.firstinspires.ftc.teamcode.Wrappers.BetterServo;
import org.opencv.features2d.MSER;

public class DropDown extends IServoModule {

    public static boolean rightServoReversed=false;

    public static double takeElementPosition=0.4 , rampUpPosition=0.6 , rampDownPosition=0.2;

    public static double MaxVelocoty=20 , Acceleration=32  , Deceleration=32;

    public DropDown()
    {
        moduleName="DropDOWN";
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
        states.addState("takeElement" , takeElementPosition);
        states.addState("rampUp" , rampUpPosition);
        states.addState("rampDown" , rampDownPosition);

        states.addState("goTakeElement" , states.get("takeElement") , takeElementPosition);
        states.addState("goRampUp" , states.get("rampUp") , rampUpPosition);
        states.addState("goRampDown" , states.get("rampDown") , rampDownPosition);


    }

    @Override
    public void updateStatesPosition(){
        states.get("takeElement").updatePositions(takeElementPosition);
        states.get("goTakeElement").updatePositions(takeElementPosition);

        states.get("rampUp").updatePositions(rampUpPosition);
        states.get("goRampUp").updatePositions(rampUpPosition);

        states.get("rampDown").updatePositions(rampDownPosition);
        states.get("goRampDown").updatePositions(rampDownPosition);
    }

    @Override
    public void atStart()  {

    }

}
