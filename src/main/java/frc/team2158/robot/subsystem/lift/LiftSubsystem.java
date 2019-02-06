package frc.team2158.robot.subsystem.lift;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2158.robot.command.lift.MoveLift;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import java.util.logging.Logger;
/**
 * @author William Blount
 * @version 0.0.1
 * This class initializes and runs our Lift speed and direction.  
 */
public class LiftSubsystem extends Subsystem {
    private static final Logger LOGGER = Logger.getLogger(LiftSubsystem.class.getName());

    public enum Direction {UP, DOWN}

    private Joystick joystickOperator = new Joystick(1);
    private SpeedController liftSpeedController;
    public static double DEFAULT_LIFT_UP_SPEED = 1.0;
    public static double DEFAULT_LIFT_DOWN_SPEED = 0.75;
    private CANPIDController m_pidController;
    private CANEncoder m_encoder;
    private double rotations;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    /**
     * Initializes our Lift subsystem.
     * @param liftSpeedController controller to be initialized.
     * @param inverted If the lift is inverted or not
     */
    public LiftSubsystem(CANSparkMax liftSpeedController) {
        this.liftSpeedController = liftSpeedController;
        m_pidController = liftSpeedController.getPIDController();

        // Encoder object created to display position values
        m_encoder = liftSpeedController.getEncoder();
    
        // PID coefficients
        kP = 0.1; 
        kI = 1e-4;
        kD = 1; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;
    
        // set PID coefficients
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        //m_pidController.setOutputRange(kMinOutput, kMaxOutput);
        // double p = SmartDashboard.getNumber("P Gain", 0);
        // double i = SmartDashboard.getNumber("I Gain", 0);
        // double d = SmartDashboard.getNumber("D Gain", 0);
        // double iz = SmartDashboard.getNumber("I Zone", 0);
        // double ff = SmartDashboard.getNumber("Feed Forward", 0);
        // double max = SmartDashboard.getNumber("Max Output", 0);
        // double min = SmartDashboard.getNumber("Min Output", 0);
        // rotations = SmartDashboard.getNumber("Set Rotations", 0);
        // if((p != kP)) { m_pidController.setP(p); kP = p; }
        // if((i != kI)) { m_pidController.setI(i); kI = i; }
        // if((d != kD)) { m_pidController.setD(d); kD = d; }
        // if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
        // if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
        // //if((max != kMaxOutput) || (min != kMinOutput)) { 
          //m_pidController.setOutputRange(min, max); 
          //kMinOutput = min; kMaxOutput = max; 
        //}
        
       
    
       // liftSpeedController.setInverted(inverted);
        LOGGER.info("Lift subsystem initialization complete!");
    }
    /**
     * Moves the lift.
     * @param direction Direction specified.
     * @param speed Speed specified.
     */
    public void moveLift(Direction direction, double speed) {
                if(joystickOperator.getRawAxis(3) < -.05){
                    rotations = rotations - .1;
                    m_pidController.setReference(rotations, ControlType.kPosition);
                    SmartDashboard.putNumber("SetPoint", rotations);
                    SmartDashboard.putNumber("ProcessVariable", m_encoder.getPosition());
                }
                else if(joystickOperator.getRawAxis(3) > .05){
                    rotations = rotations + .1;
                    m_pidController.setReference(rotations, ControlType.kPosition);
                    SmartDashboard.putNumber("SetPoint", rotations);
                    SmartDashboard.putNumber("ProcessVariable", m_encoder.getPosition());
                }
    }
    /**
     * Stops the lift by setting the speed to zero.
     */
    public void stopLift() {
       
    }
    
    @Override
    protected void initDefaultCommand() {
       setDefaultCommand(new MoveLift());
    }
}
