package org.firstinspires.ftc.teamcode.Modules.Outtake;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.internal.network.ApChannel;
import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.opencv.core.Mat;

public class Outtake {

    public enum State{
        GoDown ,
        Deafult ,
        TakingElement ,
        Specimen ,
        DeafultWithElement,
        GoReleaseSample, ReleaseSample ,
        Up
    }
    public State state=State.Deafult;

    enum Scoring{

        HighBasket ,
        LowBasket ,
        HighChamber, ScoringHighChamber,
        LowChamber , ScoringLowChamber
    } public Scoring scoring=Scoring.LowChamber;

    public boolean haveSample=false;
    boolean high=true;

    public Arm arm;
    Lift lift;
    public Claw claw;

    boolean fromFront=false;
    boolean scoringFromFront=false;

    public static int lowBasketPosition , highBasketPosition , lowChamberUp , lowChamberDown , highChamberUp , highChamberDown;

    public Outtake()
    {
        arm=new Arm();
        lift=new Lift();
        claw=new Claw();
    }


    public void grabElement()
    {
        switch (state)
        {
            case Deafult:
                arm.setState("goDeposit");
                break;

            case DeafultWithElement:
            case TakingElement:
            case Up:
                break;
        }
        state=State.TakingElement;
    }

    public void takeSpecimen()
    {
        if(state==State.Up || state==State.DeafultWithElement || state==State.TakingElement)return;
        state=State.Specimen;
    }

    public void takeSample()
    {
        if(state==State.Up || state==State.DeafultWithElement || state==State.TakingElement)return;
        haveSample=true;
        state=State.Deafult;
    }
    public void goDefault()
    {
        haveSample=true;
        state=State.Deafult;
    }

    public void goForHigh()
    {
        high=true;
    }
    public void goForLow()
    {
        high=false;
    }

    public void GoReleaseSample(){

        if(state==State.DeafultWithElement && haveSample==true)state=State.GoReleaseSample;
    }
    public void ReleaseSpecimen(){
        if(state==State.GoReleaseSample && arm.inPosition())state=State.ReleaseSample;
    }

    public void score(){
        if(state!=State.Up || !arm.inPosition())return;
        if(scoring==Scoring.LowBasket || scoring==Scoring.HighBasket)
        {
            state=State.GoDown;
        }

        if(scoring== Scoring.LowChamber)scoring=Scoring.ScoringLowChamber;
        if(scoring==Scoring.HighChamber)scoring=Scoring.ScoringHighChamber;
    }


    public void goUp()
    {
        if(state==State.DeafultWithElement)
        state=State.Up;
    }


    private void updateHardware()
    {
        switch (state)
        {
            case Specimen:
                if(claw.state==claw.states.get("open"))
                    if(!fromFront)
                arm.setState("goTakeSpecimenBack");
                else
                    arm.setState("goTakeSpecimenFront");
                claw.setState("goOpen");
                lift.goDown();
                break;
            case TakingElement:
                if(arm.inPosition())claw.setState("goClose");
                if(claw.inPosition() && claw.state==claw.states.get("close"))state=State.DeafultWithElement;
                lift.goDown();
                break;
            case Deafult:
                if(claw.inPosition() && claw.state==claw.states.get("open"))
                arm.setState("goDefault");
                claw.setState("goOpen");
                lift.goDown();
                break;
            case DeafultWithElement:
                arm.setState("goWithElement");
                claw.setState("goClose");
                lift.goDown();
                break;
            case GoReleaseSample:
                if(Math.abs(Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS))<Math.PI/2)arm.setState("goReleaseSampleFront");
                else arm.setState("goReleaseSampleBack");
                lift.goDown();
                break;
            case ReleaseSample:
                if(arm.inPosition())claw.setState("goOpen");
                if(claw.inPosition())state=State.Deafult;
                lift.goDown();
                break;
            case GoDown:
                claw.setState("goOpen");
                if(claw.inPosition()) state= State.Deafult;
                    break;
            case Up:
            {

                if(haveSample==true) {

                    double angle=Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

                    angle=(2*Math.PI+angle)- Math.floor((2*Math.PI+angle)/(Math.PI*2))*Math.PI*2;

                    if(angle>Math.PI*1/4 && angle<Math.PI*5/4)
                        scoringFromFront=true;
                    else scoringFromFront=false;
                }
                else{

                    if(Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS)<0)scoringFromFront=true;
                    else scoringFromFront=false;
                }
                switch (scoring)
                {
                    case LowBasket:
                        lift.goUp();
                        lift.setPosition(lowBasketPosition);
                        if(scoringFromFront==true)arm.setState("goLowSampleFront");
                        else arm.setState("goLowSampleBack");
                        break;
                    case HighBasket:
                        lift.goUp();
                        lift.setPosition(highBasketPosition);
                        if(scoringFromFront==true)arm.setState("goHighSampleFront");
                        else arm.setState("goHighSampleBack");
                        break;
                    case LowChamber:
                        lift.goUp();
                        if(scoringFromFront==true)arm.setState("goLowSpecimenFront");
                        else arm.setState("goLowSpecimenBack");

                        if(scoringFromFront==true)
                        {
                            if(fromFront==true)lift.setPosition(lowChamberUp);
                            else lift.setPosition(lowChamberDown);
                        }
                        else
                        {
                            if(fromFront==true)lift.setPosition(lowChamberDown);
                            else lift.setPosition(lowChamberUp);
                        }
                        break;
                    case HighChamber:
                        lift.goUp();
                        if(scoringFromFront==true)arm.setState("goHighSpecimenFront");
                        else arm.setState("goHighSpecimenBack");

                        if(scoringFromFront==true)
                        {
                            if(fromFront==true)lift.setPosition(highChamberUp);
                            else lift.setPosition(highChamberDown);
                        }
                        else
                        {
                            if(fromFront==true)lift.setPosition(highChamberDown);
                            else lift.setPosition(highChamberUp);
                        }
                        break;
                    case ScoringLowChamber:
                        if(scoringFromFront==true)
                        {
                            if(fromFront==true)lift.setPosition(lowChamberDown);
                            else lift.setPosition(lowChamberUp);
                        }
                        else
                        {
                            if(fromFront==true)lift.setPosition(lowChamberUp);
                            else lift.setPosition(lowChamberDown);
                        }
                        if(lift.inPosition())state=State.GoDown;

                        break;
                    case ScoringHighChamber:
                        if(scoringFromFront==true)
                        {
                            if(fromFront==true)lift.setPosition(highChamberDown);
                            else lift.setPosition(highChamberUp);
                        }
                        else
                        {
                            if(fromFront==true)lift.setPosition(highChamberUp);
                            else lift.setPosition(highChamberDown);
                        }
                        if(lift.inPosition())state=State.GoDown;

                        break;

                }
            }break;
        }
    }

    private void updateUpState()
    {
        if((scoring==Scoring.ScoringHighChamber || scoring==Scoring.ScoringLowChamber) && state==State.Up)return;
        if(haveSample)
        {
            if(high)scoring=Scoring.HighBasket;
            if(!high)scoring=Scoring.LowBasket;
        }
        else{
            if(high)scoring=Scoring.HighChamber;
            if(!high)scoring=Scoring.LowChamber;
        }
    }
    private void updateSpecimen()
    {
        if(state!=State.Specimen)return;

        if(Math.abs(Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS))>Math.PI/2)
        {
            fromFront=true;
            haveSample=false;
        }
        else{
            fromFront=false;
            haveSample=false;
        }
    }


    public void update()
    {

        arm.update();
        claw.update();
        lift.update();

        updateSpecimen();
        updateUpState();
        updateHardware();
    }
}
