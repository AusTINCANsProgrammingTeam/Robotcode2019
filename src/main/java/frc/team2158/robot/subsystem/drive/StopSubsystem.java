package frc.team2158.robot.subsystem.drive;

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
public class StopSubsystem extends Subsystem {
    private static final Logger LOGGER = Logger.getLogger(StopSubsystem.class.getName());

    public enum StopDirection {UP, DOWN, CLOCKWISE, COUNTERCLOCKWISE}
    public enum PivotDirection {UP, DOWN}

    private static String solenoidState = "";

    private static final double DEFAULT_INTAKE_SPEED = 0.75;
    private static final double DEFAULT_INTAKE_SPEED_IN = 1.0;
    private static final double DEFAULT_INTAKE_SPEED_OUT = 0.75;

    private static final double DEFAULT_PIVOT_SPEED = 0.75;

    private SpeedController leftSpeedController;
    private SpeedController rightSpeedController;
    private SpeedController pivotSpeedController;
    private DoubleSolenoid solenoid;

    /**
     * Initializes the intake subsystem.
     * @param leftSpeedController Speed Controller
     * @param rightSpeedController peed Controller
     * @param pivotSpeedController Speed Controller
     * @param solenoid Solenoid to be used.
     */
    public StopSubsystem(DoubleSolenoid solenoid) {

        this.solenoid = solenoid;
        setDoubleSolenoidState(DoubleSolenoid.Value.kReverse);
        LOGGER.info("Stop subsystem initialization complete!");
    }

    /**
     * Run the intake in a direction.
     * @param direction Either IN, OUT, CLOCKWISE, or COUNTERCLOCKWISE
     */
    public void raiseStop(StopDirection direction) {
        switch(direction) {
            case UP:
                leftSpeedController.set(-DEFAULT_INTAKE_SPEED_IN);
                break;
            case DOWN:
                leftSpeedController.set(DEFAULT_INTAKE_SPEED_OUT);
                break;

        }
    }

    


    /**
     * Stops the intake by setting the speed controllers to 0.0
     */
    public void stopIntake() {
        setDoubleSolenoidState(DoubleSolenoid.Value.kForward);
    }


    /**
     * Pivots the intake in a specified direction
     * @param direction either UP or DOWN
     */
   

    /**
     * Stops the pivot by setting the pivot controller to 0.0
     */

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
    public void toggleSolenoidState(StopDirection direction) {
        switch(direction) {
            case DOWN:
                solenoid.set(DoubleSolenoid.Value.kReverse);
                break;
            case UP:
                solenoid.set(DoubleSolenoid.Value.kForward);
                break;
           
        }
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StopIntake());
    }
}