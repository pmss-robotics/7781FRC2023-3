package frc.robot;

public final class Constants {
    // Ports
    public static final int xboxControllerPort = 0;

    public static final int frontLeftMotorPort = 2;
    public static final int frontRightMotorPort = 3;
    public static final int rearLeftMotorPort = 1;
    public static final int rearRightMotorPort = 4;

    public static final int _leftIntakeMotorPort = 5;
    public static final int _rightIntakeMotorPort = 6;

    public static final int leftArmMotorPort = 8;
    public static final int rightArmMotorPort = 9;
    public static final int extendMotorPort = 7;

    public static final int leftSolenoidForwardChannel = 0;
    public static final int leftSolenoidReverseChannel = 1;
    public static final int rightSolenoidForwardChannel = 2;
    public static final int rightSolenoidReverseChannel = 3;

    public static double initialRotation = 0; // degrees
    public static double xChargePadOffset = 1; // meters
    public static double yChargePadOffset = 1; // meters

    // degrees travelled per second @ s = autoSpeed
    public static final double rotationConstant = 90;
    // meters travelled per second @ s = autoSpeed
    public static final double driveConstant = 0.5;
    public static final double autoSpeed = 0.5;
    public static String calibrating = "f"; // set to "d" for drive, "r" for rotate, anything else for regular
    // Robot configs
    public static final double driveSensitivity = 0.7;

}
