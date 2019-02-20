package frc.team2158.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2158.robot.command.drive.DriveMode;
import frc.team2158.robot.command.drive.OperatorControl;
import frc.team2158.robot.command.drive.ToggleGearMode;
import frc.team2158.robot.command.intake.*;
import frc.team2158.robot.command.lift.MoveLiftDown;
import frc.team2158.robot.command.lift.MoveLiftUp;
import frc.team2158.robot.subsystem.drive.DriveSubsystem;
import frc.team2158.robot.subsystem.drive.GearMode;
import frc.team2158.robot.subsystem.drive.StopSubsystem;
import frc.team2158.robot.subsystem.drive.StopSubsystem.StopDirection;
import frc.team2158.robot.subsystem.intake.IntakeSubsystem;
import frc.team2158.robot.subsystem.lift.Arm;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.team2158.robot.subsystem.lift.Arm;

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
    private static Arm liftSubsystem;
    private static IntakeSubsystem intakeSubsystem;
    private static StopSubsystem stopSubsystem;

    private static OperatorInterface operatorInterface;
    private Spark blinkin = new Spark(6);
    
    @Override
    public void disabledInit() {
        liftSubsystem.resetPos();
        SmartDashboard.putBoolean("UpLimit", liftSubsystem.getUpLimit());
        SmartDashboard.putBoolean("DownLimit", liftSubsystem.getDownLimit());
    }
    @Override
    public void disabledPeriodic(){
        SmartDashboard.putBoolean("UpLimit", liftSubsystem.getUpLimit());
        SmartDashboard.putBoolean("DownLimit", liftSubsystem.getDownLimit());
    }

    /**
     *Initializes the TalonSRX Groups, the Solenoid, Operator Interface, Lift Motors, and Intake Motors.
     */
    @Override
    public void robotInit() {
        // Initialize the drive subsystem.
        driveSubsystem = new DriveSubsystem(
            new SpeedControllerGroup(
                new CANSparkMax(RobotMap.LEFT_MOTOR_1, MotorType.kBrushless), // This motor is the master for the left side.
                new CANSparkMax(RobotMap.LEFT_MOTOR_2, MotorType.kBrushless),
                new CANSparkMax(RobotMap.LEFT_MOTOR_3, MotorType.kBrushless)
        ),
        new SpeedControllerGroup(
                new CANSparkMax(RobotMap.RIGHT_MOTOR_1, MotorType.kBrushless), // This motor is the master for the left side.
                new CANSparkMax(RobotMap.RIGHT_MOTOR_2, MotorType.kBrushless),
                new CANSparkMax(RobotMap.RIGHT_MOTOR_3, MotorType.kBrushless)
        ),
        new DoubleSolenoid(RobotMap.PCM_ADDRESS, RobotMap.GEARBOX_FORWARD_CHANNEL,
                RobotMap.GEARBOX_REVERSE_CHANNEL)
);
        
        LOGGER.info("Drive Subsystem Initialized properly!");
        // Initialize the lift subsystem.
        liftSubsystem = new Arm(
            new CANSparkMax(RobotMap.ARM_MOTOR, MotorType.kBrushless)
        );
        LOGGER.info("Lift Subsystem Initialized properly!");
        stopSubsystem = new StopSubsystem(
            new DoubleSolenoid(RobotMap.HARD_STOP_FOWARD, RobotMap.HARD_STOP_BACK)
        );
        // Initialize the intake subsystem.
        intakeSubsystem = new IntakeSubsystem(
                new Spark(RobotMap.LEFT_INTAKE_MOTOR),
                new DoubleSolenoid(RobotMap.INTAKE_SOLENOID_FOWARD, RobotMap.INTAKE_SOLENOID_REVERSE)
        );
        LOGGER.info("Intake Subsystem Initialized properly!");
        // Initialize the operator interface.
        operatorInterface = new OperatorInterface();


        LOGGER.info("Robot initialization completed.");

        //raise the hard stop
        stopSubsystem.raiseStop(StopDirection.UP);
    }

    /**
     * Returns the instance of the drive subsystem.
     * @return the instance of the drive subsystem.
     */
    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {}

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
        if(liftSubsystem != null) {
            return liftSubsystem;
        }
        throw new RuntimeException("Lift subsystem has not yet been initialized!");
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
        

        operatorInterface.bindButton("buttonRB", OperatorInterface.ButtonMode.WHILE_HELD, new Intake(),1);
        operatorInterface.bindButton("buttonRT", OperatorInterface.ButtonMode.WHILE_HELD, new Outtake(), 1);
        operatorInterface.bindButton("button2", OperatorInterface.ButtonMode.WHEN_PRESSED, new ToggleGearMode(), 1);
        operatorInterface.bindButton("button1", OperatorInterface.ButtonMode.WHEN_PRESSED, new MoveLiftUp(), 1);
        operatorInterface.bindButton("button3", OperatorInterface.ButtonMode.WHEN_PRESSED, new MoveLiftDown(), 1);
        operatorInterface.bindButton("buttonLB", OperatorInterface.ButtonMode.WHILE_HELD, new IntakeHalfSpeed(), 1);
        operatorInterface.bindButton("buttonLT", OperatorInterface.ButtonMode.WHILE_HELD, new OuttakeHalfSpeed(), 1);
        operatorInterface.bindButton("buttonA", OperatorInterface.ButtonMode.WHEN_PRESSED, new ToggleGearMode(), 0);
        
        Scheduler.getInstance().add(new OperatorControl(DriveMode.ARCADE));
    // Stretch Goal: Make the button bindings come from an xml/json config.
    //how would we implement such a system?
}
    /**
     * Runs the TeleOp Periodic code.
     */


    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("SetPoint", liftSubsystem.getRotations());
        SmartDashboard.putNumber("ProcessVariable", liftSubsystem.getPos());
    }
}