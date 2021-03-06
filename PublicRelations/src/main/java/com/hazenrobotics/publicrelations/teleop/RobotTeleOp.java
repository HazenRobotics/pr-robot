package com.hazenrobotics.publicrelations.teleop;

import com.hazenrobotics.commoncode.input.ButtonManager;
import com.hazenrobotics.commoncode.input.Toggle;
import com.hazenrobotics.commoncode.interfaces.OpModeInterface;
import com.hazenrobotics.commoncode.movement.DrivingController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="PR Teleop", group="PR")
public class RobotTeleOp extends LinearOpMode implements OpModeInterface {
    //Add all global objects and lists
    protected ButtonManager buttons = new ButtonManager();

    //Add Motors, Servos, Sensors, etc here
    protected DrivingController driving;

    //Wheel Motors
    protected DcMotor leftFront;
    protected DcMotor rightFront;
    protected DcMotor leftBack;
    protected DcMotor rightBack;

    //Basket Motor
    protected  DcMotor basket;


    //Add all Constants here
    //EX: protected final double MOTOR_POWER = 0.5;
    @Override
    public void runOpMode() {
        setupHardware();
        setupButtons();
        //Add any further initialization (methods) here

        waitForStart();

        while (opModeIsActive()) {
            buttons.update();

            drive();
            basket.setPower(gamepad1.left_stick_y/9);


            telemetry.addData("Basket Position:", basket.getCurrentPosition());


            telemetry.update();
            idle();
        }
    }
    protected void setupHardware() {
        //Initializes the motor/servo variables here
        basket = getMotor("basket");
        basket.setDirection(DcMotor.Direction.REVERSE);

        leftFront = getMotor("leftFront");
        rightFront = getMotor("rightFront");
        leftBack = getMotor("leftBack");
        rightBack = getMotor("rightBack");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

    }

    protected void setupButtons() {
        buttons = new ButtonManager();
    }

    protected void drive() {
       // telemetry.addData("servo pos: ", flicker.getPosition());

        //left stick controls movement
        //right stick controls turning

        double turn_x = gamepad1.right_stick_x; //stick that determines how far robot is turning
        double magnitude = Math.abs(gamepad1.left_stick_y) + Math.abs(gamepad1.left_stick_x) + Math.abs(turn_x); //Used to determine the greatest possible value of y +/- x to scale them
        double scale = Math.max(1, magnitude); //Used to prevent setting motor to power over 1
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.right_stick_y;


        double leftFrontPower = (y + x + turn_x) / scale;
        double rightFrontPower = (y - x - turn_x) / scale;
        double leftBackPower = (y - x + turn_x) / scale;
        double rightBackPower = (y + x - turn_x) / scale;

        //setting power for each of the 4 wheels
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);

    }

    protected void debugging(){
        leftFront.setPower(-gamepad1.left_stick_y);
        leftBack.setPower(-gamepad2.left_stick_y);
        rightFront.setPower(-gamepad1.right_stick_y);
        rightBack.setPower(-gamepad2.right_stick_y);
        telemetry.addData("Left Forward Power:", " " + leftFront.getPower());
        telemetry.addData("Right Forward Power:", " " + rightFront.getPower());
        telemetry.addData("Left Backward Power:", " " + leftBack.getPower());
        telemetry.addData("Right Backward Power:", " " + rightBack.getPower());
        telemetry.update();
        idle();
    }

    @Override
    public Telemetry getTelemetry() {
        return null;
    }

    @Override
    public Gamepad getGamepad1() {
        return gamepad1;
    }

    @Override
    public Gamepad getGamepad2() {
        return gamepad2;
    }

    @Override
    public DcMotor getMotor(String name) {
        return hardwareMap.dcMotor.get(name);
    }

    @Override
    public Servo getServo(String name) {
        return hardwareMap.servo.get(name);
    }

    @Override
    public DigitalChannel getDigitalChannel(String name) {
        return hardwareMap.digitalChannel.get(name);
    }

    @Override
    public HardwareDevice get(String name) {
        return hardwareMap.get(name);
    }
}
