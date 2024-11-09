package org.firstinspires.ftc.teamcode.basicProgram;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;

/**
 * This is a TeleOp program for the mecanum drive robot
 */
@TeleOp
public class mecanumDrive extends OpMode {

    // Creates a timer object
    private ElapsedTime runtime = new ElapsedTime();

    // Declares motor variables
    private DcMotor frontRight = null;
    private DcMotor backRight = null;
    private DcMotor frontLeft = null;
    private DcMotor backLeft = null;

    /**
     * runs once when you initialize the program
     */
    @Override
    public void init() {
        // Displaying that the robot is starting
        telemetry.addData("Status", "Initializing");

        // Sets the hardware variables
        frontRight = hardwareMap.get(DcMotor.class, Constants.DriveConstants.FRONT_RIGHT_MOTOR_NAME);
        backRight = hardwareMap.get(DcMotor.class, Constants.DriveConstants.BACK_RIGHT_MOTOR_NAME);
        frontLeft  = hardwareMap.get(DcMotor.class, Constants.DriveConstants.FRONT_LEFT_MOTOR_NAME);
        backLeft = hardwareMap.get(DcMotor.class, Constants.DriveConstants.BACK_LEFT_MOTOR_NAME);

        // setting the motor direction
        frontRight.setDirection(Constants.DriveConstants.FRONT_RIGHT_MOTOR_DIRECTION);
        backRight.setDirection(Constants.DriveConstants.BACK_RIGHT_MOTOR_DIRECTION);
        frontLeft.setDirection(Constants.DriveConstants.FRONT_LEFT_MOTOR_DIRECTION);
        backLeft.setDirection(Constants.DriveConstants.BACK_LEFT_MOTOR_DIRECTION);

        //Setting modem mode (brake mode)
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Displaying that the robot has started
        telemetry.addData("Status", "Initialized");
    }

    /**
     * Runs once when you press start
     */
    @Override
    public void start() {
        // Sets runtime to 0
        runtime.reset();
    }

    /**
     * Starts on run, loops forever
     */
    @Override
    public void loop() {
        // Gets the controller joystick input
        double horizontal = -gamepad1.left_stick_x;
        double vertical = gamepad1.left_stick_y;
        double pivot = -gamepad1.right_stick_x;

        // Slow Mode
        if(gamepad1.right_bumper) {
            horizontal /= 3;
            vertical /= 3;
            pivot /= 3;
        }

        //drive controls/calculations
        frontRight.setPower(-pivot + (vertical - horizontal));
        backRight.setPower(-pivot + (vertical + horizontal));
        frontLeft.setPower(pivot + (vertical + horizontal));
        backLeft.setPower(pivot + (vertical - horizontal));

        // Update the display variables
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "horizontal (%.2f), vertical (%.2f), pivot (%.2f)", horizontal, vertical, pivot);
    }
}
