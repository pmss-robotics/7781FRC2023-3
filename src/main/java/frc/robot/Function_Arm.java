/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;

// Angus Jan. 31: set up all the motors
/**
 * Add your docs here.
 */
public class Function_Arm {
    // establish motors
    // feb 18 also establishes solenoids and compressor
    WPI_VictorSPX _leftArmMotor = new WPI_VictorSPX(Constants.leftArmMotorPort);
    WPI_VictorSPX _rightArmMotor = new WPI_VictorSPX(Constants.rightArmMotorPort);
    WPI_VictorSPX _extendMotor = new WPI_VictorSPX(Constants.extendMotorPort);
    DoubleSolenoid _leftSolenoid = new DoubleSolenoid(null, 0, 1);
    DoubleSolenoid _rightSolenoid = new DoubleSolenoid(null, 0, 1);
    Compressor compressor = new Compressor(null);
    public void driveSetup() {
		compressor.enableDigital();
		/* Set Neutral mode */
		_leftArmMotor.stopMotor();
        _leftArmMotor.setInverted(false);
        _rightArmMotor.stopMotor();
        _rightArmMotor.setInverted(false);
        _extendMotor.stopMotor();
        _extendMotor.setInverted(false);
        
        _leftSolenoid.set(Value.kOff);
        _rightSolenoid.set(Value.kOff);
    }
    
    /*
     * Params
     * lift: Positive values move arm up, negative vice versa
     * extend: Positive values bring the arm out, negatice vice versa   
     */
    public void liftPeriodic(double lift, double extend, double open) {
        //Alyn Jul 13, changed parameters of drivePeriodic to fit Mecanum driveCartesian
        _leftArmMotor.set(lift);
        _rightArmMotor.set(lift);
        _extendMotor.set(extend);
        //Needs explanation
        Value solenoidValue = open > 0 ? Value.kForward : (open < 0 ? Value.kReverse : Value.kOff);
        _leftSolenoid.set(solenoidValue);
        _rightSolenoid.set(solenoidValue);
    }
}
