package frc.team2158.robot.subsystem.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team2158.robot.command.intake.StopIntake;

import java.util.logging.Logger;
/**
 * @author William Blount
 * @version 0.0.1
 * This subsystem manages the cube intake motors and solenoids.
 */
public class IntakeSubsystem extends Subsystem {
    private static final Logger LOGGER = Logger.getLogger(IntakeSubsystem.class.getName());

    public enum IntakeDirection {IN, OUT, CLOCKWISE, COUNTERCLOCKWISE}
    public enum PivotDirection {UP, DOWN}

    private static String solenoidState = "";

    private static final double DEFAULT_INTAKE_SPEED = 0.75;
    private static final double DEFAULT_INTAKE_SPEED_IN = 1.0;
    private static final double DEFAULT_INTAKE_SPEED_OUT = 0.75;

    private static final double DEFAULT_PIVOT_SPEED = 0.75;

    private SpeedController leftSpeedController;
    private SpeedController pivotSpeedController;
    private DoubleSolenoid solenoid;

    /**
     * Initializes the intake subsystem.
     * @param leftSpeedController Speed Controller
     * @param rightSpeedController peed Controller
     * @param pivotSpeedController Speed Controller
     * @param solenoid Solenoid to be used.
     */
    public IntakeSubsystem(SpeedController intakeSpeedController,DoubleSolenoid solenoid) {
        this.leftSpeedController = intakeSpeedController;
        this.solenoid = solenoid;
        setDoubleSolenoidState(DoubleSolenoid.Value.kForward);
    }

    /**
     * Run the intake in a direction.
     * @param direction Either IN, OUT, CLOCKWISE, or COUNTERCLOCKWISE
     */
    public void runIntake(IntakeDirection direction) {
        switch(direction) {
            case IN:
                leftSpeedController.set(-DEFAULT_INTAKE_SPEED_IN);
                break;
            case OUT:
                leftSpeedController.set(DEFAULT_INTAKE_SPEED_OUT);
                break;

        }
    }
    public void runIntakeHalfSpeed(IntakeDirection direction){
        switch(direction){
            case IN:
                leftSpeedController.set(-DEFAULT_INTAKE_SPEED*.75);
                break;
            case OUT:
                leftSpeedController.set(DEFAULT_INTAKE_SPEED*.625);
                break;
        }
    }


    /**
     * Stops the intake by setting the speed controllers to 0.0
     */
    public void stopIntake() {
        leftSpeedController.set(0.0);
    }

    public void stopIntakeAndPivot(){
        stopIntake();
        stopPivot();
    }

    /**
     * Pivots the intake in a specified direction
     * @param direction either UP or DOWN
     */
    public void pivotIntake(PivotDirection direction) {
        switch(direction) {
            case UP:
                pivotSpeedController.set(-DEFAULT_PIVOT_SPEED); //the reason these are reversed is because the motor
                                                                //on the robot is reversed
                break;
            case DOWN:
                pivotSpeedController.set(DEFAULT_PIVOT_SPEED);
                break;
        }
    }

    /**
     * Stops the pivot by setting the pivot controller to 0.0
     */
    public void stopPivot() {
        pivotSpeedController.set(0.0);
    }

    /**
     * Set the Intake Solenoid states
     * @param state Either kOff, kForward, or kReverse
     */
    public void setDoubleSolenoidState(DoubleSolenoid.Value state) {
        solenoid.set(state);
    }

    public DoubleSolenoid.Value getSolenoidState(){
        return solenoid.get();
    }

    /**
     * Toggles the solenoid state.
     */
    public void toggleSolenoidState() {
        switch(solenoid.get()) {
            case kForward:
                setDoubleSolenoidState(DoubleSolenoid.Value.kReverse);
                break;
            case kReverse:
                setDoubleSolenoidState(DoubleSolenoid.Value.kForward);
                break;
            case kOff:
                break;
        }
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StopIntake());
    }
}
