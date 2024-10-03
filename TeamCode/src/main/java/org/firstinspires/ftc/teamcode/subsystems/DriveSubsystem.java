package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.bosch.BHI260IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import org.firstinspires.ftc.teamcode.*;

public class DriveSubsystem extends SubsystemBase {

    private HardwareMap hardwareMap;

    private final DcMotorEx frontLeftMotor;
    private final DcMotorEx frontRightMotor;
    private final DcMotorEx backLeftMotor;
    private final DcMotorEx backRightMotor;

    private double speedMultiplier = 1.0;

    public DriveSubsystem(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        frontLeftMotor = hardwareMap.get(DcMotorEx.class, Constants.DriveConstants.FRONT_LEFT_MOTOR_NAME);
        frontRightMotor = hardwareMap.get(DcMotorEx.class, Constants.DriveConstants.FRONT_RIGHT_MOTOR_NAME);
        backLeftMotor = hardwareMap.get(DcMotorEx.class, Constants.DriveConstants.BACK_LEFT_MOTOR_NAME);
        backRightMotor = hardwareMap.get(DcMotorEx.class, Constants.DriveConstants.BACK_RIGHT_MOTOR_NAME);

        frontLeftMotor.setDirection(Constants.DriveConstants.FRONT_LEFT_MOTOR_DIRECTION);
        frontRightMotor.setDirection(Constants.DriveConstants.FRONT_RIGHT_MOTOR_DIRECTION);
        backLeftMotor.setDirection(Constants.DriveConstants.BACK_LEFT_MOTOR_DIRECTION);
        backRightMotor.setDirection(Constants.DriveConstants.BACK_RIGHT_MOTOR_DIRECTION);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetEncoders();
    }
    
    public void drive(double forward, double rotate) {
        forward = forward >= Constants.DriveConstants.DEADZONE ? forward : 0;
        rotate = rotate >= Constants.DriveConstants.DEADZONE ? rotate : 0;

        backLeftMotor.setPower(Range.clip((forward + rotate), -1, 1) * speedMultiplier);
        backRightMotor.setPower(Range.clip((forward - rotate), -1, 1) * speedMultiplier);
    }

    public void setSpeedMultiplier(double multiplier) {
        speedMultiplier = Range.clip(multiplier, 0, 1);
    }

    public void resetEncoders() {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
