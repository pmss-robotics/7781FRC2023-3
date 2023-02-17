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
public class Function_Intake {
    // establish motors
    WPI_VictorSPX _leftIntakeMotor = new WPI_VictorSPX(6);
    WPI_VictorSPX _rightIntakeMotor = new WPI_VictorSPX(5);
    public void driveSetup() {
		
		/* Set Neutral mode */
		_leftIntakeMotor.stopMotor();
        _rightIntakeMotor.stopMotor();
        _leftIntakeMotor.setInverted(false);
        _rightIntakeMotor.setInverted(true);
        
        //for mecanum, each wheel moves independently.
        //change setInverted for each direction for stick
        /* Configure output direction */
        
		/*_leftIntakeMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
		_rightIntakeMotor.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward*/
        
    }
    
    public void grabPeriodic(double grab) {
        //Alyn Jul 13, changed parameters of drivePeriodic to fit Mecanum driveCartesian
        _leftIntakeMotor.set(grab);
        _rightIntakeMotor.set(grab);
    }
}
