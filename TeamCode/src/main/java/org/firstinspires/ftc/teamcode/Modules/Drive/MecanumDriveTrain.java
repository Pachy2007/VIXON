package org.firstinspires.ftc.teamcode.Modules.Drive;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Math.PIDController;
import org.firstinspires.ftc.teamcode.Math.PidAngle;
import org.firstinspires.ftc.teamcode.Math.PidToPoint;
import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Wrappers.BetterMotor;
import org.firstinspires.ftc.teamcode.Wrappers.Pose2D;
import org.opencv.core.Mat;

import java.util.Vector;

public class MecanumDriveTrain {

    enum State{
        DRIVE ,
        PID
    } State state;

    Vector2d vector;
    double angular;

    Pose2D targetPosition;



    TwoWheelTrackingLocalizer localizer=new TwoWheelTrackingLocalizer();
    public static boolean frontLeftreversed=false , frontRightreversed=false , backLeftreversed=false , backRightreversed=false;

    BetterMotor frontLeft , frontRight , backLeft , backRight;

    public MecanumDriveTrain()
    {

        frontLeft=new BetterMotor(Hardware.mch0 , BetterMotor.RunMode.RUN , frontLeftreversed);
        frontRight=new BetterMotor(Hardware.mch0 , BetterMotor.RunMode.RUN , frontRightreversed);

        backLeft=new BetterMotor(Hardware.mch0 , BetterMotor.RunMode.RUN , backLeftreversed);
        backRight=new BetterMotor(Hardware.mch0 , BetterMotor.RunMode.RUN , backRightreversed);

        PidToPoint.init();
        PidAngle.init();
    }


    public void setTargetPosition(Pose2D position)
    {
        targetPosition=position;
    }

    public void setPower(double x , double y , double rotation)
    {
        vector=new Vector2d(x ,y);
        angular=rotation;
    }


    private void updateHardware()
    {
        double heading=Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double sinus=Math.sin(-heading);
        double cosinus=Math.cos(-heading);

        double x=vector.getX();
        double y=vector.getY();

        double X=cosinus*x - sinus*y;
        double Y=sinus*x + cosinus*y;

        
    }

    public void update()
    {
        localizer.update();

        if(state==State.PID){
        vector= PidToPoint.calculateVector(
                localizer.getPoseEstimate().getX() , localizer.getPoseEstimate().getY() ,
                targetPosition.x , targetPosition.y
                );
        angular= PidAngle.calculate(Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) , targetPosition.heading);}

        updateHardware();
    }
}
