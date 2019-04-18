package frc.team2158.robot.subsystem.lift;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
public class PistonLiftSubsystem extends Subsystem {

    private DoubleSolenoid FowardSolenoid_1;
    private DoubleSolenoid FowardSolenoid_2;
    private DoubleSolenoid BackSolenoid_1;
    private DoubleSolenoid BackSolenoid_2;
    private Boolean enabled = false;


    private static final Logger LOGGER = Logger.getLogger(Robot.class.getName());


    public PistonLiftSubsystem(DoubleSolenoid FowardSolenoid_1, DoubleSolenoid FowardSolenoid_2, DoubleSolenoid BackSolenoid_1, 
                                DoubleSolenoid backSolenoid_2){
        this.FowardSolenoid_1 = FowardSolenoid_1;
        this.FowardSolenoid_2 = FowardSolenoid_2;
        this.BackSolenoid_1 = BackSolenoid_1;
        this.BackSolenoid_2 = backSolenoid_2;
        this.BackSolenoid_1.set(Value.kForward);
        this.BackSolenoid_2.set(Value.kForward);
        this.FowardSolenoid_1.set(Value.kReverse);
        this.FowardSolenoid_2.set(Value.kReverse);


    }

    public void enable(){
        enabled = true;
        SmartDashboard.putBoolean("PistonClimb", true);

    }

    public void toggleFowardPistons(){
        LOGGER.info("Forward");
        if(enabled){
          switch(FowardSolenoid_1.get()){
            case kForward:
                FowardSolenoid_1.set(Value.kReverse);
                FowardSolenoid_2.set(Value.kReverse);
                break;
            case kReverse:
                FowardSolenoid_1.set(Value.kForward);
                FowardSolenoid_2.set(Value.kForward);
                break;
            case kOff:
                break;
        }
    }
    }

    public void toggleBackPistons(){
        LOGGER.info("Backward");
        if(enabled){
         switch(BackSolenoid_1.get()){
            case kForward:
                BackSolenoid_1.set(Value.kReverse);
                BackSolenoid_2.set(Value.kReverse);
                break;
            case kReverse:
                BackSolenoid_1.set(Value.kForward);
                BackSolenoid_2.set(Value.kForward);
                break;
            case kOff:
                break;
        }
    }
    }
    
    
    
    @Override
    protected void initDefaultCommand() {
    }
}