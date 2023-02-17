package frc.robot;

// Phoenix library

//import edu.wpi.first.wpilibj.motorcontrol.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.apriltag.AprilTagDetector;
//Alyn Jul 13, input Mecanum drive

public class Function_Drive {
    /* MOTORS*/
	// Setting ID for motors
	// Drive train motors
	// TalonSRXs are masters; Victors are slaves

/*     // Set ID for front left motor as TalonSRX SRX 3
    TalonSRX _frontLeftMotor = new TalonSRX(3);
    // Set ID for front right motor as TalonSRX SRX 6
    TalonSRX _frontRightMotor = new TalonSRX(6);
    // Set ID for left slave motor as Victor SPX 4
    TalonSRX _backLeftMotor = new TalonSRX(4);
    // Set ID for right slave motor as Victor SPX 5
    TalonSRX _backRightMotor = new TalonSRX(5);
    DifferentialDrive _drive = new DifferentialDrive(_frontLeftMotor, _frontRightMotor); */

    // Alyn Jul 13, define driveA.
    // MUST CHANGE NUMBER ONCE IDed
    // Set ID for front left motor as TalonSRX SRX 2
    WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(2);
    // Set ID for front right motor as TalonSRX SRX 3
    WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(3);
    // Set ID for rear left motor as TalonSRX SPX 1
    WPI_TalonSRX _rearLeftMotor = new WPI_TalonSRX(1);
    // Set ID for rear right motor as TalonSRX SPX 4
    WPI_TalonSRX _rearRightMotor = new WPI_TalonSRX(4);
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

    

   // MecanumDrive _driveA = new MecanumDrive(_frontLeftMotor, _rearLeftMotor, _frontRightMotor, _rearRightMotor);
  DifferentialDrive _driveA = new DifferentialDrive(_frontLeftMotor,_frontRightMotor);
  DifferentialDrive _driveB = new DifferentialDrive(_rearLeftMotor,_rearRightMotor);


    public void driveSetup() {
		
		/* Set Neutral mode */
		//_frontLeftMotor.stopMotor();
        //_frontRightMotor.stopMotor();
        //_rearLeftMotor.stopMotor();
		//_rearRightMotor.stopMotor();
        
        //for mecanum, each wheel moves independently.
        //change setInverted for each direction for stick
        /* Configure output direction */
        
        _frontLeftMotor.setSafetyEnabled(false);
        _frontRightMotor.setSafetyEnabled(false);
		_frontLeftMotor.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward
		_frontRightMotor.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward
        _rearLeftMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
		_rearRightMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
        
    }
    
    public void drivePeriodic(double xSpeed, double zRotation, boolean speedy) {
        //Alyn Jul 13, changed parameters of drivePeriodic to fit Mecanum driveCartesian
        //_driveA.driveCartesian(ySpeed, xSpeed, zRotation);
       // _driveA.arcadeDrive(xSpeed, 4);
        //_driveA.tankDrive(ySpeed, 20);
            _driveA.arcadeDrive(xSpeed, zRotation, false);
        
    }

    public void driveAutonomous() {
        _driveA.arcadeDrive(0, 1);
    }
}