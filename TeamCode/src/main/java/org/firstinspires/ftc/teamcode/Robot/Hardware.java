package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {

    public static boolean INIT=false;

    public static DcMotorEx mch0 , mch1 , mch2 , mch3;

    public static DcMotorEx meh0 , meh1 , meh2 , meh3;

    public static Servo sch0 , sch1 , sch2 , sch3 , sch4 , sch5;
    public static Servo seh0 , seh1 , seh2 , seh3 , seh4 , seh5;

    public DigitalChannel beamBreak0, beamBreak1;


    public static IMU imu;

    public static void init(HardwareMap hardwareMap)
    {
        if(INIT)return;
        INIT=true;
        mch0=hardwareMap.get(DcMotorEx.class , "ch0");
        mch1=hardwareMap.get(DcMotorEx.class , "ch1");
        mch2=hardwareMap.get(DcMotorEx.class , "ch2");
        mch3=hardwareMap.get(DcMotorEx.class , "ch3");

        meh0=hardwareMap.get(DcMotorEx.class , "eh0");
        meh1=hardwareMap.get(DcMotorEx.class , "eh1");
        meh2=hardwareMap.get(DcMotorEx.class , "eh2");
        meh3=hardwareMap.get(DcMotorEx.class , "eh3");

        sch0=hardwareMap.get(Servo.class , "sch0");
        sch1=hardwareMap.get(Servo.class , "sch1");
        sch2=hardwareMap.get(Servo.class , "sch2");
        sch3=hardwareMap.get(Servo.class , "sch3");
        sch4=hardwareMap.get(Servo.class , "sch4");
        sch5=hardwareMap.get(Servo.class , "sch5");

        seh0=hardwareMap.get(Servo.class , "seh0");
        seh1=hardwareMap.get(Servo.class , "seh1");
        seh2=hardwareMap.get(Servo.class , "seh2");
        seh3=hardwareMap.get(Servo.class , "seh3");
        seh4=hardwareMap.get(Servo.class , "seh4");
        seh5=hardwareMap.get(Servo.class , "seh5");


    }

}