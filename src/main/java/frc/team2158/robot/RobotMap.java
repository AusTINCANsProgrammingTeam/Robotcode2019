package frc.team2158.robot;

/**
 * @version 0.0.1
 * @author William Blount
 * This class stores numbers vital to the proper operation of the robot. 
 * **Beware when editing.**
 */
public class RobotMap {
  
    public static final int LEFT_MOTOR_1 = 5;
    public static final int LEFT_MOTOR_2 = 6;
    public static final int LEFT_MOTOR_3 = 7;

    public static final int RIGHT_MOTOR_1 = 2;
    public static final int RIGHT_MOTOR_2 = 3;
    public static final int RIGHT_MOTOR_3 = 4;

    // Pneumatic system.
    public static final int PCM_ADDRESS = 1;
    public static final int GEARBOX_FORWARD_CHANNEL = 0;
    public static final int GEARBOX_REVERSE_CHANNEL = 1;

    // Lift motors Sparks.
    public static final int ARM_MOTOR = 9;

    // Intake motors and pneumatics.
    public static final int LEFT_INTAKE_MOTOR = 3;
    public static final int INTAKE_SOLENOID_1 = 2;
    public static final int INTAKE_SOLENOID_2 = 3;

    // Port for the joystick.
    //Stretch Goal: Move this out and make a XML based input config system.
    public static final int JOYSTICK_PORT = 0;

}
