package frc.team2158.robot;

import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team2158.robot.command.drive.DriveMode;
import frc.team2158.robot.command.drive.OperatorControl;
import frc.team2158.robot.command.drive.ToggleGearMode;
import frc.team2158.robot.command.intake.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2158.robot.subsystem.drive.DriveSubsystem;
import frc.team2158.robot.subsystem.drive.GearMode;
import frc.team2158.robot.subsystem.drive.StopSubsystem;
import frc.team2158.robot.subsystem.drive.StopSubsystem.StopDirection;
import frc.team2158.robot.subsystem.intake.IntakeSubsystem;
import frc.team2158.robot.subsystem.lift.Arm;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.cscore.UsbCamera;
import frc.team2158.robot.subsystem.drive.SparkMaxGroup;
import frc.team2158.robot.subsystem.lift.SelfLift;
import frc.team2158.robot.command.lift.RunSelfLift;
import frc.team2158.robot.command.lift.ChangeLimit;

import java.util.logging.Logger;
//TODO Rename some classes <- Billy's job.
//TODO Lua macros

/**
 * @author William Blount
 * @version 0.0.1
 * The main class of our code.
 * Initializes the teleOperated code.
 */
public class Robot extends TimedRobot {
    private SendableChooser<Double> autoChooser;
    private static final Logger LOGGER = Logger.getLogger(Robot.class.getName());
    private static final LoggingSystem LOGGING_SYSTEM = LoggingSystem.getInstance();

    private static DriveSubsystem driveSubsystem;
    private static Arm armSubsystem;
    private static IntakeSubsystem intakeSubsystem;
    private static StopSubsystem stopSubsystem;
    private static SelfLift selfLiftSubsystem;

    private static OperatorInterface operatorInterface;
    
    //this runs after robotinit
    @Override
    public void disabledInit() {
        //will this ^^^ be run before the match starts? 
        //because there may be issues if it tries to reset itself while being locked by a solenoid
        SmartDashboard.putBoolean("UpLimit", armSubsystem.getUpLimit());
    }

    @Override
    public void disabledPeriodic(){
        SmartDashboard.putBoolean("UpLimit", armSubsystem.getUpLimit());
    }

    /**
     *Initializes the TalonSRX Groups, the Solenoid, Operator Interface, Lift Motors, and Intake Motors.
     */
    @Override
    public void robotInit() {
        UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera1.setResolution(320, 240);
        //UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        //camera2.setResolution(320, 240);
        // Initialize the drive subsystem.
        driveSubsystem = new DriveSubsystem(
            new SparkMaxGroup(
                new CANSparkMax(RobotMap.LEFT_MOTOR_1, MotorType.kBrushless), // This motor is the master for the left side.
                new CANSparkMax(RobotMap.LEFT_MOTOR_2, MotorType.kBrushless),
                new CANSparkMax(RobotMap.LEFT_MOTOR_3, MotorType.kBrushless)
        ),
        new SparkMaxGroup(
                new CANSparkMax(RobotMap.RIGHT_MOTOR_1, MotorType.kBrushless), // This motor is the master for the right side.
                new CANSparkMax(RobotMap.RIGHT_MOTOR_2, MotorType.kBrushless),
                new CANSparkMax(RobotMap.RIGHT_MOTOR_3, MotorType.kBrushless)
        ),
        new DoubleSolenoid(RobotMap.PCM_ADDRESS, RobotMap.GEARBOX_FORWARD_CHANNEL,
                RobotMap.GEARBOX_REVERSE_CHANNEL)
);
Robot.getDriveSubsystem().setGearMode(GearMode.LOW);
        
        LOGGER.info("Drive Subsystem Initialized properly!");
        // Initialize the arm subsystem.
        armSubsystem = new Arm(RobotMap.ARM_MOTOR
            //new CANSparkMax(RobotMap.ARM_MOTOR, MotorType.kBrushless)
        );
        selfLiftSubsystem = new SelfLift(RobotMap.SELF_LIFT_MOTOR_1, RobotMap.SELF_LIFT_MOTOR_2);
        LOGGER.info("Arm Subsystem Initialized properly!");
        stopSubsystem = new StopSubsystem(
            new DoubleSolenoid(RobotMap.PCM_ADDRESS, RobotMap.HARD_STOP_FOWARD, RobotMap.HARD_STOP_BACK)
        );
        // Initialize the intake subsystem.
        intakeSubsystem = new IntakeSubsystem(
                new Spark(RobotMap.LEFT_INTAKE_MOTOR),
                new DoubleSolenoid(RobotMap.PCM_ADDRESS, RobotMap.INTAKE_SOLENOID_FOWARD, RobotMap.INTAKE_SOLENOID_REVERSE)
        );
        LOGGER.info("Intake Subsystem Initialized properly!");
        // Initialize the operator interface.
        operatorInterface = new OperatorInterface();


        LOGGER.info("Robot initialization completed.");
        
        //raise the hard stop
        stopSubsystem.raiseStop(StopDirection.UP); //do we need to run this? it initializes with the value already up
    
    }

    /**
     * Returns the instance of the drive subsystem.
     * @return the instance of the drive subsystem.
     */
    @Override
    public void autonomousInit() {
        armSubsystem.resetPos(); 
        setup();
    }

    @Override
    public void autonomousPeriodic() {
        periodic();
    }

    public static DriveSubsystem getDriveSubsystem() {
        if(driveSubsystem != null) {
            return driveSubsystem;
        }
        throw new RuntimeException("Drive subsystem has not yet been initialized!");
    }

    /**
     * Returns the instance of the lift subsystem.
     * @return the instance of the lift subsystem.
     */
    public static Arm getLiftSubsystem() {
        if(armSubsystem != null) {
            return armSubsystem;
        }
        throw new RuntimeException("Lift subsystem has not yet been initialized!");
    }

    public static SelfLift getSelfLiftSubsystem() {
        if(selfLiftSubsystem != null){
            return selfLiftSubsystem;
        }
        throw new RuntimeException("Self Lift subsystem has not yet been initialized!");
    }

    /**
     * Returns the instance of the intake subsystem.
     * @return the instance of the intake subsystem.
     */
    public static IntakeSubsystem getIntakeSubsystem() {
        if(intakeSubsystem != null) {
            return intakeSubsystem;
        }
        throw new RuntimeException("Intake subsystem has not yet been initialized!");
    }
    public static StopSubsystem getStopSubsystem(){
        if(stopSubsystem != null){
            return stopSubsystem;
        }
        throw new RuntimeException("Stop susbsystem has not been initialized!");
    }

    /**
     * Returns the instance of the Operator Interface.
     * @return the instance of the Operator Interface.
     */
    public static OperatorInterface getOperatorInterface() {
        return operatorInterface;
    }

    /**
     * Initializes the TeleOp Robot State and binds the buttons that will be used in the controller.
     */
    @Override
    public void teleopInit() {

        LOGGER.info("Teleop Init!");
        setup();
    }

    /**
     * Runs the TeleOp Periodic code.
     */


    @Override
    public void teleopPeriodic() {
        periodic();
    }

    public void periodic(){
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("SetPoint", armSubsystem.getRotations());
        SmartDashboard.putNumber("ProcessVariable", armSubsystem.getPos());
    }

    public void setup(){
        operatorInterface.bindButton("buttonRB", OperatorInterface.ButtonMode.WHILE_HELD, new Intake(),1);
        operatorInterface.bindButton("buttonRT", OperatorInterface.ButtonMode.WHILE_HELD, new Outtake(), 1);
        operatorInterface.bindButton("button2", OperatorInterface.ButtonMode.WHEN_PRESSED, new ToggleHatchSolenoid(), 1);
        //operatorInterface.bindButton("button1", OperatorInterface.ButtonMode.WHEN_PRESSED, new MoveLiftUp(), 1);
        //operatorInterface.bindButton("button3", OperatorInterface.ButtonMode.WHEN_PRESSED, new MoveLiftDown(), 1);
        operatorInterface.bindButton("buttonLB", OperatorInterface.ButtonMode.WHILE_HELD, new IntakeHalfSpeed(), 1);
        operatorInterface.bindButton("buttonLT", OperatorInterface.ButtonMode.WHILE_HELD, new OuttakeHalfSpeed(), 1);
        operatorInterface.bindButton("buttonB", OperatorInterface.ButtonMode.WHEN_PRESSED, new ToggleGearMode(), 0);
        //operatorInterface.bindButton("button4", OperatorInterface.ButtonMode.WHEN_PRESSED, new RunSelfLift(), 1);
        //operatorInterface.bindButton("buttonBack", OperatorInterface.ButtonMode.WHEN_PRESSED, new RunSelfLift2nd(), 1);
        operatorInterface.bindButton("buttonStart", OperatorInterface.ButtonMode.WHEN_PRESSED, new ChangeLimit(), 1);


        
        Scheduler.getInstance().add(new OperatorControl(DriveMode.ARCADE));
        
        getStopSubsystem().raiseStop(StopDirection.UP);
        }
}