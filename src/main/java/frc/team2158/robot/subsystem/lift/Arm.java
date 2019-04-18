package frc.team2158.robot.subsystem.lift;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2158.robot.Robot;
import frc.team2158.robot.command.lift.MoveLift;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;

import java.util.logging.Logger;
/**
 * @author William Blount
 * @version 0.0.1
 * This class initializes and runs our Lift speed and direction.  
 */
public class Arm extends Subsystem {
    private static final Logger LOGGER = Logger.getLogger(Arm.class.getName());

    public enum Direction {UP, DOWN}

    private CANSparkMax liftSpeedController;
    public static double DEFAULT_LIFT_UP_SPEED = 1.0;
    public static double DEFAULT_LIFT_DOWN_SPEED = 0.75;
    private CANPIDController m_pidController;
    private CANEncoder m_encoder;
    private CANDigitalInput upLimit;
    private CANDigitalInput downLimit;
    private static double rotations;
    private boolean lift = false;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public double topLimit = 20;
    public double bottomLimit = -27;
    public boolean armRestricted = true;
    /**
     * Initializes our Lift subsystem.
     * @param controller controller to be initialized.
     * @param inverted If the lift is inverted or not
     */
    public Arm(int deviceId) {
        this.liftSpeedController = new CANSparkMax(deviceId, MotorType.kBrushless);
        liftSpeedController.restoreFactoryDefaults();
        m_pidController = liftSpeedController.getPIDController();
        upLimit = liftSpeedController.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
        downLimit = liftSpeedController.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
        // Encoder object created to display position values
        m_encoder = liftSpeedController.getEncoder();
        rotations = m_encoder.getPosition();
        /*if (rotations < -0.45)
        {
            rotations = -.45;
        }
        m_pidController.setReference(rotations, ControlType.kPosition);*/
    
        // PID coefficients
        kP = .1;
        kI = 1e-4;
        kD = 1.5; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 5; 
        kMinOutput = -.5;
    
        // set PID coefficients
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);
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
    }
    /**
     * Moves the lift.
     * @param direction Direction specified.
     * @param speed Speed specified.
     */
    public void moveLift() {
                if(Robot.getOperatorInterface().getOperatorController().getRawAxis(1) < -.15 && upLimit.get() == false && !armRestricted){
                    if(rotations < topLimit){
                         rotations = rotations + .4 * Math.abs(Robot.getOperatorInterface().getOperatorController().getRawAxis(1));
                    }                   
                    //LOGGER.warning(Double.toString(Robot.getOperatorInterface().getOperatorController().getRawAxis(3)));
                    //LOGGER.warning(rotations + "");
                    m_pidController.setReference(rotations, ControlType.kPosition);
                }
                else if(Robot.getOperatorInterface().getOperatorController().getRawAxis(1) > .15 && downLimit.get() == false && !armRestricted){
                    if(rotations > bottomLimit){
                        rotations = rotations - .4 * Math.abs(Robot.getOperatorInterface().getOperatorController().getRawAxis(1));
                    }
                    //LOGGER.warning(Double.toString(Robot.getOperatorInterface().getOperatorController().getRawAxis(3)));
                    m_pidController.setReference(rotations, ControlType.kPosition);
                }
    }

    public void changeBottomLimit(){
        if(lift == false){
            bottomLimit = -37;
            m_pidController.setOutputRange(-1, 1);
            liftSpeedController.setSmartCurrentLimit(80);
            lift = true;
        }

    }
    /**
     * Stops the lift by setting the speed to zero.
     */
    public void stopLift() {
       
    }

    public void resetPos(){
       m_encoder.setPosition(0);
       m_pidController.setReference(0, ControlType.kPosition);
       rotations = 0;
       //LOGGER.warning("encoderPos: "+ Double.toString(m_encoder.getPosition()));
    }

    public double getPos(){
       return  m_encoder.getPosition();
    }

    public double getRotations(){
        return rotations;
    }
    
    @Override
    protected void initDefaultCommand() {
       setDefaultCommand(new MoveLift());
    }

    public boolean getUpLimit()
    {
        return upLimit.get();
    }

    public boolean getDownLimit(){
        return downLimit.get();
    }

    public void restrictArmMotor(boolean hardStopUp)
    {
        if (hardStopUp)
        {
            armRestricted = true;
            m_pidController.setOutputRange(0, 0);
        }
        else
        {
            armRestricted = false;
            m_pidController.setOutputRange(-.5, .5);
        }
    }
}
