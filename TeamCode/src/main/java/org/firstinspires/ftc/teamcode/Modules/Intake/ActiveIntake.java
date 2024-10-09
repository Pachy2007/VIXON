package org.firstinspires.ftc.teamcode.Modules.Intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Robot.Hardware;
import org.firstinspires.ftc.teamcode.Wrappers.BetterMotor;

public class ActiveIntake {

    public static double repausPower , intakePower , reversePower;

    public enum State{
        REPAUS(repausPower) , INTAKE(intakePower) , REVERSE(reversePower);

        double power;
        State(double power)
        {
            this.power=power;
        }
    }
    State state;

    public static boolean reversed=false;
    BetterMotor motor;

    public ActiveIntake()
    {
        motor= new BetterMotor(Hardware.mch0 , BetterMotor.RunMode.RUN , reversed);
        state=State.REPAUS;
    }


    public void setMode(State state)
    {
        this.state=state;
        motor.setPower(state.power);
    }
}
