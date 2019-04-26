package frc.team2158.robot.command.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2158.robot.Robot;
import frc.team2158.robot.subsystem.intake.IntakeSubsystem;
import frc.team2158.robot.subsystem.lift.PistonLiftSubsystem;

import java.util.logging.Logger;

/**
 * @author William Blount
 * @version 0.0.1
 * Toggles the intake Solenoid
 */
public class ToggleBackPistons extends Command {
    private static final Logger LOGGER = Logger.getLogger(ToggleBackPistons.class.getName());
    private  PistonLiftSubsystem pistonLiftSubsystem;

    /**
     * Instantiates the command
     */
    public ToggleBackPistons() {
        this.pistonLiftSubsystem = Robot.getPistonLiftSubsystem();
    }

    /**
     * Runs the command
     */
    @Override
    protected void execute() {
        pistonLiftSubsystem.toggleBackPistons();    
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
