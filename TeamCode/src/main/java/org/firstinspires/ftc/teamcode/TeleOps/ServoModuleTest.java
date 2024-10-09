package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Modules.Intake.Ramp;
import org.firstinspires.ftc.teamcode.Robot.Hardware;


@TeleOp
public class ServoModuleTest extends LinearOpMode {


    @Override
    public void runOpMode() {
        Hardware.init(hardwareMap);

        Ramp ramp=new Ramp();



        while (opModeInInit())
        {
            ramp.update();
        }

        waitForStart();



        while(opModeIsActive())
        {
            if(gamepad1.a)ramp.setState("goingUp");
            if(gamepad1.b)
                ramp.setState("goingDown");

            ramp.update();


            ramp.telemetry(telemetry);
            telemetry.update();
        }


    }
}
