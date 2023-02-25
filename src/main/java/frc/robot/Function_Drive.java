package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

// Phoenix library

//import edu.wpi.first.wpilibj.motorcontrol.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

        // Play around with this for setting breaks on raiser arm

    }

    public void drivePeriodic(double xSpeed, double zRotation) {
        // Alyn Jul 13, changed parameters of drivePeriodic to fit Mecanum
        // driveCartesian
        // _driveA.driveCartesian(ySpeed, xSpeed, zConstants.initialRotation);
        // _driveA.arcadeDrive(xSpeed, 4);
        // _driveA.tankDrive(ySpeed, 20);
        _driveA.arcadeDrive(xSpeed, zRotation, false);

    }

    public void driveBrake() {
        _rearLeftMotor.setNeutralMode(NeutralMode.Coast);
        _rearRightMotor.setNeutralMode(NeutralMode.Coast);
        _frontLeftMotor.setNeutralMode(NeutralMode.Coast);
        _frontRightMotor.setNeutralMode(NeutralMode.Coast);

    }

    public void driveAutonomous(double time) {
        final double[] command_values = {
                Math.signum(Constants.xChargePadOffset)
                        * -(90 + Math.signum(Constants.xChargePadOffset) * Constants.initialRotation),
                Math.abs(Constants.xChargePadOffset), Math.signum(Constants.xChargePadOffset) * 90,
                Constants.yChargePadOffset };
        final String[] commands = { "r", "d", "r", "d" };
        if (Constants.calibrating == "d") {
            if (time <= 15) {
                _driveA.arcadeDrive(0.5, 0);
            }
        } else if (Constants.calibrating == "r") {
            if (time <= 15) {
                _driveA.arcadeDrive(0, 0.5);
            }
        } else {

            // Constants.xChargePadOffset - how far right the robot is from the charge pad
            // (negative means it is to the left)
            // Constants.yChargePadOffset - how far back the robot is from the charge pad
            // Constants.initialRotation - angle offset from north in degrees (west =
            // negative, east =
            // positive)
            // time - the current tick number (use seconds for # of seconds)
            double seconds = time;
            double tsum = 0;
            for (int i = 0; i < (c + 1); i++) {
                tsum += Math.abs(command_values[i]
                        / ((commands[i] == "r") ? Constants.rotationConstant : Constants.driveConstant));
            }
            if (seconds < tsum) {
                _driveA.arcadeDrive((commands[c] == "d" ? (0.5 * Math.signum(command_values[c])) : 0),
                        (commands[c] == "r" ? (0.5 * Math.signum(command_values[c])) : 0));
            } else if ((c + 1) < commands.length) {
                c++;
            }
            /*
             * if (Constants.xChargePadOffset < 0) {
             * // all of these variables are in terms of seconds
             * // r variables are Constants.initialRotation actions, d variables are drive
             * 
             * final double r1 = (90 - Constants.initialRotation) /
             * Constants.rotationConstant;
             * final double d2 = Math.abs(Constants.xChargePadOffset) /
             * Constants.driveConstant;
             * final double r3 = 90 / Constants.rotationConstant;
             * final double d4 = Constants.yChargePadOffset / Constants.driveConstant;
             * 
             * 
             * 
             * 
             * if (seconds < r1) {
             * _driveA.arcadeDrive(0, Constants.autoSpeed);
             * } else if (seconds < r1 + d2) {
             * _driveA.arcadeDrive(Constants.autoSpeed, 0);
             * } else if (seconds < r1 + d2 + r3) {
             * _driveA.arcadeDrive(0, -Constants.autoSpeed);
             * } else if (seconds < r1 + d2 + r3 + d4) {
             * _driveA.arcadeDrive(Constants.autoSpeed, 0);
             * }
             * 
             * }
             * else if (Constants.xChargePadOffset > 0) {
             * // all of these variables are in terms of seconds
             * // r variables are Constants.initialRotation actions, d variables are drive
             * final double r1 = (90 + Constants.initialRotation) /
             * Constants.rotationConstant;
             * final double d2 = Math.abs(Constants.xChargePadOffset) /
             * Constants.driveConstant;
             * final double r3 = 90 / Constants.rotationConstant;
             * final double d4 = Constants.yChargePadOffset / Constants.driveConstant;
             * if (seconds < r1) {
             * _driveA.arcadeDrive(0, -Constants.autoSpeed);
             * } else if (seconds < r1 + d2) {
             * _driveA.arcadeDrive(Constants.autoSpeed, 0);
             * } else if (seconds < r1 + d2 + r3) {
             * _driveA.arcadeDrive(0, Constants.autoSpeed);
             * } else if (seconds < r1 + d2 + r3 + d4) {
             * _driveA.arcadeDrive(Constants.autoSpeed, 0);
             * }
             * } else {
             * final double d1 = Constants.yChargePadOffset / Constants.driveConstant;
             * if (seconds < d1) {
             * _driveA.arcadeDrive(Constants.autoSpeed, 0);
             * }
             * }
             */
        }
        /*
         * If you want to do limelight
         * NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
         * NetworkTableEntry tx = table.getEntry("tx");
         * NetworkTableEntry ty = table.getEntry("ty");
         * NetworkTableEntry ta = table.getEntry("ta");
         * // read values periodically
         * double x = tx.getDouble(0.0);
         * double y = ty.getDouble(0.0);
         * double area = ta.getDouble(0.0);
         * 
         * // post to smart dashboard periodically
         * SmartDashboard.putNumber("LimelightX", x);
         * SmartDashboard.putNumber("LimelightY", y);
         * SmartDashboard.putNumber("LimelightArea", area);
         */
    }
}