package org.firstinspires.ftc.teamcode.Wrappers;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Math.BetterMotionProfile;

public class BetterServo {

    public enum RunMode{
        PROFILE,
        GoToPosition;
    }
    RunMode runMode;

    BetterMotionProfile profile;

    public double position;

    public boolean reachedPosition=true;

    Servo servo;

    public BetterServo(Servo servo , RunMode runMode , double initialPosition , boolean reversed)
    {
        this.servo=servo;
        this.runMode=runMode;
        this.servo.setPosition(initialPosition);
        if(reversed)servo.setDirection(Servo.Direction.REVERSE);
        position=initialPosition;
    }
    public BetterServo(Servo servo , RunMode runMode  , boolean reversed)
    {
        this.servo=servo;
        this.runMode=runMode;
        if(reversed)servo.setDirection(Servo.Direction.REVERSE);
    }


    public void setProfileCoefficients(double maxVelocity , double acceleration , double deceleration)
    {
        if(runMode!=RunMode.PROFILE)return;

        profile=new BetterMotionProfile(maxVelocity , acceleration , deceleration);
        profile.setMotion(servo.getPosition() , servo.getPosition() , 0);
    }

    public void setPosition(double position)
    {
        if(position==this.position)return;
        switch (runMode)
        {
            case PROFILE:
                reachedPosition=false;
                if(position!= profile.finalPosition)
                profile.setMotion(profile.getPosition() , position , profile.getVelocity());
                break;
            case GoToPosition:
                reachedPosition=true;
                servo.setPosition(position);
                this.position=position;
                break;
        }
    }
    public void setMODE(RunMode mode)
    {
        runMode=mode;
    }



    public double getPosition()
    {
        return position;
    }

    public boolean inPosition()
    {
        return reachedPosition;
    }





    public void update()    /* Add this function to the loop if you use motionprofile */
    {
        if(runMode!=RunMode.PROFILE)return;
        if(profile.finalPosition==servo.getPosition())  reachedPosition=true;
        else reachedPosition=false;

        profile.update();
        if(profile.getPosition()!=servo.getPosition())
        servo.setPosition(profile.getPosition());
        position=profile.getPosition();

    }

}
