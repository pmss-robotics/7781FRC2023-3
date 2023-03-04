package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

// Phoenix library

//import edu.wpi.first.wpilibj.motorcontrol.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.apriltag.AprilTagDetector;
//Alyn Jul 13, input Mecanum drive

public class Function_Drive {

    /* MOTORS */
    // Setting ID for motors
    // Drive train motors
    // TalonSRXs are masters; Victors are slaves

    /*
     * // Set ID for front left motor as TalonSRX SRX 3
     * TalonSRX _frontLeftMotor = new TalonSRX(3);
     * // Set ID for front right motor as TalonSRX SRX 6
     * TalonSRX _frontRightMotor = new TalonSRX(6);
     * // Set ID for left slave motor as Victor SPX 4
     * TalonSRX _backLeftMotor = new TalonSRX(4);
     * // Set ID for right slave motor as Victor SPX 5
     * TalonSRX _backRightMotor = new TalonSRX(5);
     * DifferentialDrive _drive = new DifferentialDrive(_frontLeftMotor,
     * _frontRightMotor);
     */

    // Alyn Jul 13, define driveA.
    // MUST CHANGE NUMBER ONCE IDed
    // Set ID for front left motor as TalonSRX SRX 2
    WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(Constants.frontLeftMotorPort);
    // Set ID for front right motor as TalonSRX SRX 3
    WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(Constants.frontRightMotorPort);
    // Set ID for rear left motor as TalonSRX SPX 1
    WPI_TalonSRX _rearLeftMotor = new WPI_TalonSRX(Constants.rearLeftMotorPort);
    // Set ID for rear right motor as TalonSRX SPX 4
    WPI_TalonSRX _rearRightMotor = new WPI_TalonSRX(Constants.rearRightMotorPort);
    AprilTagDetector detector = new AprilTagDetector();
    MotorControllerGroup _leftMotors = new MotorControllerGroup(_frontLeftMotor, _rearLeftMotor);
    MotorControllerGroup _rightMotors = new MotorControllerGroup(_frontRightMotor, _rearRightMotor);
    Gyro gyro = new AnalogGyro(0);
    // //attempted to fix
    // // Alyn Jul 13, define driveA.
    // // MUST CHANGE NUMBER ONCE IDed
    // Set ID for front left motor as TalonSRX SRX 2
    // TalonSRX _frontLeftMotor = new TalonSRX(2);
    // // Set ID for front right motor as TalonSRX SRX 3
    // TalonSRX _frontRightMotor = new TalonSRX(3);
    // // Set ID for rear left motor as TalonSRX SPX 6
    // TalonSRX _rearLeftMotor = new TalonSRX(1);
    // // Set ID for rear right motor as TalonSRX SPX 1
    // TalonSRX _rearRightMotor = new TalonSRX(6);

    // MecanumDrive _driveA = new MecanumDrive(_frontLeftMotor, _rearLeftMotor,
    // _frontRightMotor, _rearRightMotor);
    DifferentialDrive _driveA = new DifferentialDrive(_frontLeftMotor, _frontRightMotor);
    int c = 0;

    public void driveSetup() {

        /* Set Neutral mode */
        // _frontLeftMotor.stopMotor();
        // _frontRightMotor.stopMotor();
        // _rearLeftMotor.stopMotor();
        // _rearRightMotor.stopMotor();

        // for mecanum, each wheel moves independently.
        // change setInverted for each direction for stick
        /* Configure output direction */

        _frontLeftMotor.setSafetyEnabled(false);
        _frontRightMotor.setSafetyEnabled(false);
        _frontLeftMotor.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward
        _frontRightMotor.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward
        _rearLeftMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
        _rearRightMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
        _frontLeftMotor.setNeutralMode(NeutralMode.Coast);
        _frontRightMotor.setNeutralMode(NeutralMode.Coast);
        _rearLeftMotor.setNeutralMode(NeutralMode.Coast);
        _rearRightMotor.setNeutralMode(NeutralMode.Coast);
        // Play around with this for setting breaks on raiser arm

    }

    public void brakeMotors(boolean brake) {
        if (brake) {
            _frontLeftMotor.setNeutralMode(NeutralMode.Brake);
            _frontRightMotor.setNeutralMode(NeutralMode.Brake);
            _rearLeftMotor.setNeutralMode(NeutralMode.Brake);
            _rearRightMotor.setNeutralMode(NeutralMode.Brake);
        } else {
            _frontLeftMotor.setNeutralMode(NeutralMode.Coast);
            _frontRightMotor.setNeutralMode(NeutralMode.Coast);
            _rearLeftMotor.setNeutralMode(NeutralMode.Coast);
            _rearRightMotor.setNeutralMode(NeutralMode.Coast);
        }
    }

    public void drivePeriodic(double xSpeed, double zRotation) {
        // Alyn Jul 13, changed parameters of drivePeriodic to fit Mecanum
        // driveCartesian
        // _driveA.driveCartesian(ySpeed, xSpeed, zConstants.initialRotation);
        // _driveA.arcadeDrive(xSpeed, 4);
        // _driveA.tankDrive(ySpeed, 20);
        _driveA.arcadeDrive(xSpeed, zRotation, false);
    }

    public void driveAutonomous(double time) {
        if (time < 3) {
            _driveA.arcadeDrive(1, 0);
        } else {
            while (Math.abs(gyro.getAngle()) > 1) {
                _driveA.arcadeDrive(-gyro.getAngle() / 180 * 2, 0);
            }
        }
        /*
         * final double[] command_values = {
         * Constants.offset,
         * Math.signum(Constants.xChargePadOffset)
         * (90 + Math.signum(Constants.xChargePadOffset) * Constants.initialRotation),
         * Math.abs(Constants.xChargePadOffset), Math.signum(Constants.xChargePadOffset)
         * * -90,
         * Constants.yChargePadOffset };
         * final String[] commands = { "w", "w", "w", "w", "r", "d", "r", "d" };
         * switch (Constants.calibrating) {
         * case "d":
         * if (time <= Constants.caliseconds) {
         * _driveA.arcadeDrive(Constants.autoSpeed, 0);
         * }
         * break;
         * case "r":
         * if (time <= Constants.caliseconds) {
         * _driveA.arcadeDrive(0, Constants.autoSpeed);
         * }
         * break;
         * default:
         * double seconds = time;
         * double tsum = 0;
         * for (int i = 0; i < (c + 1); i++) {
         * tsum += Math.abs(command_values[i]
         * / ((commands[i] == "r") ? Constants.rotationConstant
         * : ((commands[i] == "d") ? Constants.driveConstant : 1)));
         * }
         * if (seconds < tsum) {
         * _driveA.arcadeDrive(
         * (commands[c] == "d" ? (Constants.autoSpeed * Math.signum(command_values[c]))
         * : 0),
         * (commands[c] == "r" ? (Constants.autoSpeed * Math.signum(command_values[c]))
         * : 0));
         * } else if ((c + 1) < commands.length) {
         * c++;
         * }
         * break;
         * }
         */
    }
}