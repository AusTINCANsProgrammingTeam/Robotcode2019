package frc.team2158.robot;

/**
 * @version 0.0.1
 * @author William Blount
 * This class stores numbers vital to the proper operation of the robot. 
 * **Beware when editing.**
 */
public class RobotMap {
    // Drive base motor channels.
    public static final int LEFT_MOTOR_1 = 6;
    public static final int LEFT_MOTOR_2 = 8;
    public static final int LEFT_MOTOR_3 = 5;

    public static final int RIGHT_MOTOR_1 = 3;
    public static final int RIGHT_MOTOR_2 = 2;
    public static final int RIGHT_MOTOR_3 = 1; // right motor is colliding the pcm address? nope jk

    public static final int ARM_MOTOR = 7;
    
    public static final int SELF_LIFT_MOTOR_1 = 12;
    public static final int SELF_LIFT_MOTOR_2 = 4;


    public static final int HARD_STOP_FOWARD = 2;
    public static final int HARD_STOP_BACK = 5;

    // Pneumatic system.
    public static final int PCM_ADDRESS = 1;
    public static final int GEARBOX_FORWARD_CHANNEL = 0; //0
    public static final int GEARBOX_REVERSE_CHANNEL = 7; //7

    // Lift motors Sparks.
    //public static final int LIFT_MOTOR_1 = 9;
    //public static final int LIFT_MOTOR_2 = 1;
    //public static final int LIFT_MOTOR_3 = 2;

    // Intake motors and pneumatics.
    public static final int LEFT_INTAKE_MOTOR = 0;
    
    public static final int INTAKE_SOLENOID_FOWARD = 1; //1
    public static final int INTAKE_SOLENOID_REVERSE = 6; //6

    // Port for the joystick.
    //Stretch Goal: Move this out and make a XML based input config system.
    public static final int JOYSTICK_PORT = 0;

}
