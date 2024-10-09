package org.firstinspires.ftc.teamcode.Wrappers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Math.PIDController;

public class BetterMotor {

    public enum RunMode{
        RUN ,
        PID
    }
    RunMode runMode;

    PIDController pidController;
    DcMotorEx motor;
    boolean power;

    public double targetPosition;

    public BetterMotor(DcMotorEx motor , RunMode runMode , boolean reversed)
    {
        this.motor=motor;
        this.runMode=runMode;

        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(reversed)motor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setPidCoefficients(double kp , double ki , double kd)
    {
        pidController=new PIDController(kp , ki , kd);
    }
    public void setPidCoefficients(double kp , double ki , double kd , double ff1 , double ff2)
    {
        pidController=new PIDController(kp , ki , kd , ff1 , ff2);
    }

    public void setPosition(double position)
    {
        power=false;
        targetPosition=position;
    }

    public void setPower(double power)
    {
        this.power=true;
        motor.setPower(power);
    }
    public void setRunMode(RunMode runMode)
    {
        this.runMode=runMode;
    }

    public double getPosition()
    {
        return motor.getCurrentPosition();
    }

    public double getVelocity()
    {
        return motor.getVelocity();
    }

    public void resetPosition()
    {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    public void update()
    {
        if(runMode==RunMode.RUN || power==true)return;

        double power=pidController.calculate(targetPosition , motor.getCurrentPosition());

        motor.setPower(power);


    }
}
