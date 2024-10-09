package org.firstinspires.ftc.teamcode.TeleOps;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Modules.Outtake.Outtake;
import org.firstinspires.ftc.teamcode.Robot.Hardware;

@TeleOp
public class OuttakeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        Hardware.init(hardwareMap);

        Outtake outtake=new Outtake();

        outtake.goDefault();

        while(opModeInInit())
        {
            outtake.update();
        }
        waitForStart();

        while(opModeIsActive()){


            if(gamepad1.b)outtake.takeSpecimen();

            if(gamepad1.x)outtake.goDefault();

            if(gamepad1.y)outtake.grabElement();

            if(gamepad1.dpad_up)outtake.goUp();
            if(gamepad1.dpad_left)outtake.goForHigh();
            if(gamepad1.dpad_right)outtake.goForLow();

            if(gamepad1.dpad_down)outtake.score();

            if(outtake.state== Outtake.State.DeafultWithElement && gamepad1.a)outtake.GoReleaseSample();

            outtake.arm.telemetry(telemetry);
            outtake.claw.telemetry(telemetry);
            telemetry.addData("haveSample" , outtake.haveSample);
            telemetry.addData("State" , outtake.state);
            telemetry.addData("scoring" , outtake.scoring);
            telemetry.addData("heading" , Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
            telemetry.update();
            outtake.update();
        }
    }
}
