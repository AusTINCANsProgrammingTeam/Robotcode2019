package frc.team2158.robot.subsystem.drive;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2158.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.logging.Logger;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
/**
 * @author William Blount
 * @version 0.0.1
 * This class manages our gear and drive modes.
 */
public class DriveSubsystem extends Subsystem {
    private static final Logger LOGGER = Logger.getLogger(DriveSubsystem.class.getName());

    private DifferentialDrive differentialDrive;
    private DoubleSolenoid gearboxSolenoid;
    private DoubleSolenoid gearboxSolenoid2;
    private boolean held;
    private CANSparkMax left1;
    private CANSparkMax left2;
    private CANSparkMax left3;
    private CANSparkMax right1;
    private CANSparkMax right2;
    private CANSparkMax right3;
    private SparkMaxGroup leftMotorGroup;
    private SparkMaxGroup rightMotorGroup;
    private boolean[] leftMotorCurrentTest;
    private boolean[] rightMotorCurrentTest;

    /**
     * This initializes the drive subsystem.
     * @param leftSpeedController the speedController on the left to be initialized.
     * @param rightSpeedController the speedController on the right to be initialized.
     * @param gearboxSolenoid Solenoid to be initialized.
     */
    public DriveSubsystem(int leftMotor1, int leftMotor2, int leftMotor3, int rightMotor1, int rightMotor2, int rightMotor3,
                          DoubleSolenoid gearboxSolenoid, DoubleSolenoid gearboxSolenoid2) {

        this.differentialDrive = new DifferentialDrive(
            leftMotorGroup = new SparkMaxGroup(
                    left1 = new CANSparkMax(leftMotor1, MotorType.kBrushless),
                    left2 = new CANSparkMax(leftMotor2, MotorType.kBrushless),
                    left3 = new CANSparkMax(leftMotor3, MotorType.kBrushless)),
            rightMotorGroup = new SparkMaxGroup(
                right1 = new CANSparkMax(rightMotor1, MotorType.kBrushless), 
                right2 = new CANSparkMax(rightMotor2, MotorType.kBrushless),
                right3 = new CANSparkMax(rightMotor3, MotorType.kBrushless))
            );
        //differentialDrive.setSafetyEnabled(false);
        this.gearboxSolenoid = gearboxSolenoid;
        this.gearboxSolenoid2 = gearboxSolenoid2;
        setGearMode(Value.kForward); //todo maybe this is part of the "every/other" bug?
    }

    /**
     * Sets the speed of both sides of the tank drive.
     * @param leftSpeed speed
     * @param rightSpeed speed
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }
    /**
     * Sets the speed and heading of the arcade drive.
     * @param velocity velocity
     * @param heading heading
     */
    public void arcadeDrive(double velocity, double heading) {
      
        differentialDrive.arcadeDrive(velocity, heading * .75, true);
    }

    /**
     * Returns instance of gearMode
     * @return instance of gearMode
    */
    public void holdVision(){
        held = true;
    }

    /**
     * Sets the gear mode
     * @param gearMode mode to set the gear
     */
    public void setGearMode(DoubleSolenoid.Value state) {
        gearboxSolenoid.set(state);
        gearboxSolenoid2.set(state);
    }

    public void checkSubsystem(){
        leftMotorGroup.set(.5);
        rightMotorGroup.set(.5);
        double[] leftGroup = leftMotorGroup.getCurrent();
        double[] rightGroup = rightMotorGroup.getCurrent();
        leftMotorGroup.set(0);
        rightMotorGroup.set(0);
        
        for(int i = 0; i < leftGroup.length; i++){
            if(leftGroup[i] < 30){
                leftMotorCurrentTest[i] = false;
                
            }
            else{leftMotorCurrentTest[i] = true;}
        }
        for(int j = 0; j < rightGroup.length; j++){
            if(rightGroup[j] < 30){
                rightMotorCurrentTest[j] = false;
            }
            else{rightMotorCurrentTest[j] = true;}
        }
        


    }

    /**
     * Easy way to change the gear mode after being set.
     */
    public void toggleGearMode() {
        switch(gearboxSolenoid.get()) {
            case kForward:
                setGearMode(DoubleSolenoid.Value.kReverse);
                SmartDashboard.putString("GearMode", "High");
                break;
            case kReverse:
                setGearMode(DoubleSolenoid.Value.kForward);
                SmartDashboard.putString("GearMode", "Low");
                break;
            case kOff:
                break;
        }
    }

    /**
     * Changes gear mode internally
     */

    @Override
    protected void initDefaultCommand() {
        held = false;
    }

    public void checkDriveSubsystem(){

        //check for motor current
        ////////
        //check for 
        

    }
}
