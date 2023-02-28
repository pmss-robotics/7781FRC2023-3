package frc.robot.testing;
// Arm lift 

public class R_Joystick {

}lift=Deadband(lift);
// sensitivity * lift
lift=Constants.armLiftSensitivity*lift;
double open = Deadband(gRightT - 0.5) * sensitivity;
