package frc.robot.testing;

//DeadBand
public class DeadBand {

}

double Deadband(double value) {
    // inside deadband
    if (abs(value) <= 0.05) {
        return 0;
    }
    // outside deadband
    return value;
}
