// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
/*package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class Robot extends TimedRobot {
  WPI_TalonSRX _backRightMotor = new WPI_TalonSRX(4); // Right rear motor
  WPI_TalonSRX _backLeftMotor = new WPI_TalonSRX(1); // Left rear motor
  WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(3); // Right front motor
  WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(2); // Left front motor
  XboxController controller = new XboxController(0);

  @Override
  public void autonomousInit() {
    _frontLeftMotor.setInverted(true);
    _backLeftMotor.setInverted(true);
    _backRightMotor.setInverted(true);
    _frontRightMotor.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {
    double slide = controller.getLeftY();
    double rotate = controller.getLeftX(); // figure out how to do rotate
    
   // MotorControllerGroup _leftMotorGroup = new MotorControllerGroup(_frontLeftMotor, _backLeftMotor);
   // MotorControllerGroup _rightMotorGroup = new MotorControllerGroup(_frontRightMotor, _backRightMotor);
    DifferentialDrive driveTrain = new DifferentialDrive(_backLeftMotor, _backRightMotor);
    driveTrain.arcadeDrive(slide, rotate);
  }
}

*/
// Robot.java: The main file of the robot
// - handles joystick controls

// Author: Alyn Shao
// CO-Author: Bob Xiong
// Date: Jul 13, 2021

package frc.robot;

/* LIBRARY IMPORTS */
//Import functions (written in same directory)

import static java.lang.Math.abs;

import edu.wpi.first.wpilibj.TimedRobot; //import timed robot
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;

//autonomous period.
public class Robot extends TimedRobot {

	long servoTime = System.currentTimeMillis();

	long autonomousTime = System.currentTimeMillis();

	/* DEFINING FUNCTIONS */
	Function_Drive driveTrain = new Function_Drive();
	Function_Intake intake = new Function_Intake();
	Function_Arm arm = new Function_Arm();
	Timer timer = new Timer();
	XboxController _gamepad = new XboxController(Constants.xboxControllerPort);
	int c = 0;
	double armLiftOffset = Constants.armLiftOffset;

	@Override
	public void autonomousInit() {
		autonomousTime = System.currentTimeMillis();
		timer.stop();
		timer.reset();
		timer.start();
		driveTrain.driveSetup();
		intake.driveSetup();
		arm.driveSetup();
		// intake.spinIn(true);
	}

	@Override
	public void autonomousPeriodic() {
		driveTrain.driveAutonomous(timer.get());
		// Periodic functions run 50 times a seconds, following code just runs for 3
		// secodns
		// Alyn Jul 13, autonomous period, move forward 0.5. x is forward
		// drivePeriodic(double ySpeed, double xSpeed, double grab)
		// Feb 15 this is now its own function
		// xChargePadOffset, yChargePadOffset - meters
		// rotation - degrees
		// System.out.println((System.currentTimeMillis()-autonomousTime));
		// intake.spinIn(false);
	}

	@Override
	public void teleopPeriodic() {
		// Can try to change if mapping is incorrect
		boolean map_alt = false;
		double gRightY = _gamepad.getRightY();
		double gRightX = _gamepad.getRightX();
		double gLeftT = _gamepad.getLeftTriggerAxis();
		double gRightT = _gamepad.getRightTriggerAxis();
		if (map_alt) {
			gRightY = _gamepad.getRightTriggerAxis();
			gRightX = _gamepad.getLeftTriggerAxis();
			gLeftT = _gamepad.getRightX();
			gRightT = _gamepad.getRightY();
		}
		/*
		 * 
		 */

		/*---- GAMEPAD ----*/
		// Jul 27: ONCE CHALLENGE ANNOUNCED, ADD CODE TO EACH BUTTON
		// EG. // trough servo
		// boolean changeIntakeState = _gamepad.getRawButton(1);

		// sensitivity control INVERTED ON LIANG'S CONTROLLER
		// Jul 27 Alyn: ASK BOB WHY USE THIS FORMULA FOR SENSATIVITY
		// double sensitivity = 1-( _gamepad.getThrottle() + 1)/2;
		// Holding the left trigger button on the remote will increase accel by 3 times!
		double sensitivity = ((Deadband(gLeftT) > 0) ? 1 : Constants.driveSensitivity);

		/*---- DRIVE ----*/
		// Going forwards and backwards by tracking joystick position

		double backforth = 1 * _gamepad.getLeftY();
		// Using deadband so minor joystick movements will not pass through and move the
		// robot
		backforth = Deadband(backforth);
		backforth = sensitivity * backforth;

		// Going left and right by tracking joystick position
		double leftright = 1 * _gamepad.getLeftX();
		leftright = Deadband(leftright);
		leftright = sensitivity * leftright;

		// Motor grab by tracking joystick position
		// Motor system
		int grab_intake = _gamepad.getLeftBumper() ? 1 : 0;
		int grab_outtake = _gamepad.getRightBumper() ? 1 : 0;
		int grab = grab_intake - grab_outtake;

		// How much the arm goes up by
		double lift = -gRightY + this.armLiftOffset;
		System.out.println("Right Y: " + Deadband(gRightY));
		// Testing: print
		/*
		 * System.out.println("Right Y: " + Deadband(gRightY));
		 * System.out.println("Right X: " + Deadband(gRightX));
		 * System.out.println("Left T: " + Deadband(gLeftT));
		 * System.out.println("Right T: " + Deadband(gRightT));
		 */

		lift = Deadband(lift);
		// sensitivity * lift
		lift = Constants.armLiftSensitivity * lift;
		double open = gRightT - 0.5;
		// sensitivity * extend
		double extend = -Deadband(gRightX) * Constants.armExtendSensitivity;

		// TODO: Move to constants file

		// Use driveCartesian (y, x, z) [NOT X, Y, Z] to control robot movement
		// Feb. 14 - Uses arcadeDrive (x, r) to control robot movement
		if (!_gamepad.getLeftBumper()) {
			driveTrain.drivePeriodic(backforth, leftright);
		} else {
			// left bumper button to brake
			driveTrain.driveBrake();
		}
		// Not needed
		intake.grabToggle(_gamepad.getXButtonPressed());
		intake.grabPeriodic(grab);
		// offset
		if (_gamepad.getRightBumperPressed()) {
			if (this.armLiftOffset == Constants.armLiftOffset) {
				this.armLiftOffset = 0;
			} else {
				this.armLiftOffset = Constants.armLiftOffset;
			}
		}
		arm.liftPeriodic(lift, extend, open);
	}

	/* UTILITY FUNCTIONS */
	/**
	 * Deadband 5 percent, used on the gamepad
	 * Ignores any input thats less than 5% incase the controller is "drifting"
	 */
	double Deadband(double value) {
		// inside deadband
		if (abs(value) <= 0.05) {
			return 0;
		}
		// outside deadband
		return value;
	}

}