// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {
  TalonSRX _backRightMotor = new TalonSRX(4); // Right rear motor
  TalonSRX _backLeftMotor = new TalonSRX(1); // Left rear motor
  TalonSRX _frontRightMotor = new TalonSRX(3); // Right front motor
  TalonSRX _frontLeftMotor = new TalonSRX(2); // Left front motor
  XboxController controller = new XboxController(0);

  @Override
  public void teleopPeriodic() {
    double slide = controller.getLeftY();
    double rotate = controller.getLeftX();
    
    _backRightMotor.set(ControlMode.PercentOutput, -slide);
    _backLeftMotor.set(ControlMode.PercentOutput, slide);
    _frontRightMotor.set(ControlMode.PercentOutput, slide);
    _frontLeftMotor.set(ControlMode.PercentOutput, -slide);
  }
}

