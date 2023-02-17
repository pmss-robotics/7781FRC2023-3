/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
// Angus Jan. 31: set up all the motors
/**
 * Add your docs here.
 */
public class Function_Arm {
    // establish motors
    WPI_VictorSPX _armMotor = new WPI_VictorSPX(7);
    WPI_VictorSPX _extendMotor = new WPI_VictorSPX(8);
    public void driveSetup() {
		
		/* Set Neutral mode */
		_armMotor.stopMotor();
        _armMotor.setInverted(false);

        _extendMotor.stopMotor();
        _extendMotor.setInverted(false);
        
        //for mecanum, each wheel moves independently.
        //change setInverted for each direction for stick
        /* Configure output direction */
        
		/*_leftIntakeMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
		_rightIntakeMotor.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward*/
        
    }
    
    /*
     * Params
     * lift: Positive values move arm up, negative vice versa
     * extend: Positive values bring the arm out, negatice vice versa   
     */
    public void liftPeriodic(double lift, double extend) {
        //Alyn Jul 13, changed parameters of drivePeriodic to fit Mecanum driveCartesian
        _armMotor.set(lift);
        _extendMotor.set(extend);
    }
}
