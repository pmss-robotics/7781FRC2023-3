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
// Angus Jan. 31: set up all the motors
/**
 * Add your docs here.
 */
public class Function_Arm {
    // establish motors
    // feb 18 also establishes solenoids and compressor
    WPI_VictorSPX _armMotor = new WPI_VictorSPX(7);
    WPI_VictorSPX _extendMotor = new WPI_VictorSPX(8);
    DoubleSolenoid _leftSolenoid = new DoubleSolenoid(null, 0, 1);
    DoubleSolenoid _rightSolenoid = new DoubleSolenoid(null, 0, 1);
    Compressor compressor = new Compressor(null);
    public void driveSetup() {
		compressor.enableDigital();
		/* Set Neutral mode */
		_armMotor.stopMotor();
        _armMotor.setInverted(false);

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
        _armMotor.set(lift);
        _extendMotor.set(extend);
        _leftSolenoid.set(open > 0 ? Value.kForward : (open < 0 ? Value.kReverse : Value.kOff));
        _rightSolenoid.set(open > 0 ? Value.kForward : (open < 0 ? Value.kReverse : Value.kOff));
    }
}
