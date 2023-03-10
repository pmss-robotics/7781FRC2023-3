/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

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

    DoubleSolenoid _leftSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
            Constants.leftSolenoidForwardChannel, Constants.leftSolenoidReverseChannel);
    DoubleSolenoid _rightSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
            Constants.rightSolenoidForwardChannel, Constants.rightSolenoidReverseChannel);
    Compressor compressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
    int c = 0;

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
        // Alyn Jul 13, changed parameters of drivePeriodic to fit Mecanum
        // driveCartesian
        _leftArmMotor.set(lift);
        _rightArmMotor.set(lift);
        _extendMotor.set(extend);
        // Needs explanation
        // if open is positive, then solenoids push
        // if open is negative, then solenoids retract
        Value solenoidValue = open > 0 ? Value.kForward : (open < 0 ? Value.kReverse : Value.kOff);
        _leftSolenoid.set(solenoidValue);
        _rightSolenoid.set(solenoidValue);
    }

    public void armAutonomous(double time) {
        final double[] command_values = {
                Constants.offset,
        };
        final String[] commands = { "w" };
        switch (Constants.calibrating) {
            case "l":
                if (time <= Constants.caliseconds) {
                    _leftArmMotor.set(Constants.armLiftSpeed);
                    _rightArmMotor.set(Constants.armLiftSpeed);
                }
                break;
            case "e":
                if (time <= Constants.caliseconds) {
                    _extendMotor.set(Constants.armExtendSpeed);
                }
                break;
            default:
                double seconds = time;
                double tsum = 0;
                for (int i = 0; i < (c + 1); i++) {
                    tsum += Math.abs(command_values[i]
                            / ((commands[i] == "l") ? Constants.armLiftConstant
                                    : ((commands[i] == "e") ? Constants.armExtendConstant : 1)));
                }
                if (seconds < tsum) {
                    _leftArmMotor
                            .set((commands[c] == "l") ? (Constants.armLiftSpeed * Math.signum(command_values[c])) : 0);
                    _rightArmMotor
                            .set((commands[c] == "l") ? (Constants.armLiftSpeed * Math.signum(command_values[c])) : 0);
                    _extendMotor.set(
                            (commands[c] == "e") ? (Constants.armExtendSpeed * Math.signum(command_values[c])) : 0);
                } else if ((c + 1) < commands.length) {
                    c++;
                }
                break;
        }
    }
}
