package frc.robot.testing;

//Drive
public class Right_Joystick {

}
/* Gamepad.leftY */
double backforth = 1 * _gamepad.getLeftY();
// 5% Error band
backforth=

Deadband(backforth);
backforth = sensitivity * backforth;
//Actual movement of the robot
double leftright = 1 * _gamepad.getLeftX();
leftright = Deadband(leftright);
leftright = sensitivity * leftright;
