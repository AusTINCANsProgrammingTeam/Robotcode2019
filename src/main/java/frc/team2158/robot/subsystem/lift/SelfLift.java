package frc.team2158.robot.subsystem.lift;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2158.robot.Robot;
import frc.team2158.robot.command.lift.StopSelfLift;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANDigitalInput;
import frc.team2158.robot.subsystem.drive.SparkMaxGroup;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;

import java.util.logging.Logger;
/**
 * @author William Blount
 * @version 0.0.1
 * This class initializes and runs our Lift speed and direction.  
 */
public class SelfLift extends Subsystem {
    private static final Logger LOGGER = Logger.getLogger(Arm.class.getName());

    public enum Direction {UP, DOWN}

    private SparkMaxGroup liftSpeedController;
    public static double DEFAULT_LIFT_UP_SPEED = 1.0;
    public static double DEFAULT_LIFT_DOWN_SPEED = 0.75;
    private CANPIDController m_pidController;
    private CANEncoder m_encoder;
    private CANDigitalInput upLimit;
    private CANDigitalInput downLimit;
    private static double rotations;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    /**
     * Initializes our Lift subsystem.
     * @param controller controller to be initialized.
     * @param inverted If the lift is inverted or not
     */
    public SelfLift(int deviceID_1, int deviceID_2) {
        CANSparkMax master = new CANSparkMax(deviceID_1, MotorType.kBrushless);
        liftSpeedController = new SparkMaxGroup(
            master,
            new CANSparkMax(deviceID_2, MotorType.kBrushless)
            );
        //m_pidController = liftSpeedController.getPIDController();
        // Encoder object created to display position values
        //m_encoder = liftSpeedController.getEncoder();
        rotations = m_encoder.getPosition();
        /*if (rotations < -0.45)
        {
            rotations = -.45;
        }
        m_pidController.setReference(rotations, ControlType.kPosition);*/
    
        // PID coefficients
        kP = 1;
        kI = 1e-4;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;
        
       
    
       // liftSpeedController.setInverted(inverted);
        LOGGER.info("Lift subsystem initialization complete!");
    }
    /**
     * Moves the lift.
     * @param direction Direction specified.
     * @param speed Speed specified.
     */
    public void selfLift(){  
            rotations = 5;
            m_pidController.setReference(rotations, ControlType.kPosition);
            LOGGER.warning("moveLiftPos Up");
    }
    

    /**
     * Stops the lift by setting the speed to zero.
     */
    public void stopLift() {
       
    }

    public void resetPos(){
       m_pidController.setReference(0, ControlType.kPosition);
       rotations = 0;
       m_encoder.setPosition(0);
       LOGGER.warning("encoderPos: "+ Double.toString(m_encoder.getPosition()));
    }

    public double getPos(){
       return  m_encoder.getPosition();
    }

    public double getRotations(){
        return rotations;
    }
    
    @Override
    protected void initDefaultCommand() {
       setDefaultCommand(new StopSelfLift());
    }

    public boolean getUpLimit()
    {
        return false;
        //return upLimit.get();
    }

    public boolean getDownLimit()
    {
        return false;
        //return downLimit.get();
    }
}
