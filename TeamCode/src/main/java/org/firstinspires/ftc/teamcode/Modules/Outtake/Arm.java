package org.firstinspires.ftc.teamcode.Modules.Outtake;

import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Robot.IServoModule;
import org.firstinspires.ftc.teamcode.Robot.State;
import org.firstinspires.ftc.teamcode.Wrappers.BetterServo;

public class Arm extends IServoModule {

    public static boolean leftServoReversed=false , rightServoReversed=false;

    public static double defaultLeft=0.05 , defaultRight=0.05;

    public static double depositLeft=0 , depositRight=0;

    public static double takeSpecimenBackLeft=0.1 , takeSpecimenBackRight=0.1;

    public static double takeSpecimenFrontLeft=0.2 , takeSpecimenFrontRight=0.2;

    public static double withElementLeft=0.3 , withElementRight=0.3;

    public static double releaseSampleFrontLeft=0.4 , releaseSampleFrontRight=0.4;

    public static double releaseSampleBackLeft=0.5 , releaseSampleBackRight=0.5;

    public static double lowSampleFrontLeft=0.6 , lowSampleFrontRight=0.6;

    public static double lowSampleBackLeft=0.7 , lowSampleBackRight=0.7;

    public static double highSampleFrontLeft=0.8 , highSampleFrontRight=0.8;

    public static double putHighSampleBackLeft=0.9 , putHighSampleBackRight=0.9;

    public static double lowSpecimenFrontLeft=1 , lowSpecimenFrontRight=1;

    public static double lowSpecimenBackLeft=0 , lowSpecimenBackRight=0;

    public static double highSpecimenFrontLeft=0.1 , highSpecimenFrontRight=0.1;

    public static double highSpecimenBackLeft=0.2 , highSpecimenBackRight=0.2;

    public static double MaxVelocoty=20 , Acceleration=32  , Deceleration=32;

    public Arm()
    {
        moduleName="ARM";
        setServos(
                new BetterServo("ServoLeft" , Hardware.sch1 , BetterServo.RunMode.PROFILE , leftServoReversed) ,
                new BetterServo("ServoRight" , Hardware.sch2 , BetterServo.RunMode.PROFILE , rightServoReversed)
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

        states.addState("default" , defaultLeft , defaultRight);
        states.addState("goDefault" , states.get("default") , defaultLeft , defaultRight);

        states.addState("deposit" , depositLeft , depositRight);
        states.addState("goDeposit" , states.get("deposit") , depositLeft , depositRight);

        states.addState("takeSpecimenBack" , takeSpecimenBackLeft , takeSpecimenBackRight);
        states.addState("goTakeSpecimenBack" , states.get("takeSpecimenBack") , takeSpecimenBackLeft , takeSpecimenBackRight);

        states.addState("takeSpecimenFront" , takeSpecimenFrontLeft , takeSpecimenFrontRight);
        states.addState("goTakeSpecimenFront" , states.get("takeSpecimenFront") , takeSpecimenFrontLeft , takeSpecimenFrontRight);

        states.addState("withElement" , withElementLeft, withElementRight);
        states.addState("goWithElement" , states.get("withElement") , withElementLeft, withElementRight);

        states.addState("releaseSampleFront" , releaseSampleFrontLeft, releaseSampleFrontRight);
        states.addState("goReleaseSampleFront" , states.get("releaseSampleFront") , releaseSampleFrontLeft, releaseSampleFrontRight);

        states.addState("releaseSampleBack" , releaseSampleBackLeft , releaseSampleBackRight);
        states.addState("goReleaseSampleBack" , states.get("releaseSampleBack") , releaseSampleBackLeft , releaseSampleBackRight);

        states.addState("lowSampleFront" , lowSampleFrontLeft , lowSampleFrontRight);
        states.addState("goLowSampleFront" , states.get("lowSampleFront") , lowSampleFrontLeft , lowSampleFrontRight);

        states.addState("lowSampleBack", lowSampleBackLeft , lowSampleBackRight);
        states.addState("goLowSampleBack" , states.get("lowSampleBack") , lowSampleBackLeft , lowSampleBackRight);

        states.addState("highSampleFront" , highSampleFrontLeft , highSampleFrontRight);
        states.addState("goHighSampleFront" , states.get("highSampleFront") , highSampleFrontLeft , highSampleFrontRight);

        states.addState("highSampleBack" , putHighSampleBackLeft , putHighSampleBackRight);
        states.addState("goHighSampleBack" , states.get("highSampleBack") , putHighSampleBackLeft , putHighSampleBackRight);

        states.addState("lowSpecimenFront" , lowSpecimenFrontLeft , lowSpecimenFrontRight);
        states.addState("goLowSpecimenFront" , states.get("lowSpecimenFront") , lowSpecimenFrontLeft , lowSpecimenFrontRight);

        states.addState("lowSpecimenBack" , lowSpecimenBackLeft , lowSpecimenBackRight);
        states.addState("goLowSpecimenBack" , states.get("lowSpecimenBack") , lowSpecimenBackLeft , lowSpecimenBackRight);

        states.addState("highSpecimenFront" , highSpecimenFrontLeft , highSpecimenFrontRight);
        states.addState("goHighSpecimenFront" , states.get("highSpecimenFront") , highSpecimenFrontLeft , highSpecimenFrontRight);

        states.addState("highSpecimenBack" , highSpecimenBackLeft , highSpecimenBackRight);
        states.addState("goHighSpecimenBack" , states.get("highSpecimenBack") , highSpecimenBackLeft , highSpecimenBackRight);
    }

    @Override
    public void updateStatesPosition(){

    }

    @Override
    public void atStart()  {
        state=states.get("default");
    }
}
