package frc.team2158.robot.command.intake;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2158.robot.Robot;
import frc.team2158.robot.subsystem.intake.IntakeSubsystem;

import java.util.logging.Logger;

/**
 * @author William Blount
 * @version 0.0.1
 * Toggles the intake Solenoid
 */
public class ToggleHatchSolenoid extends Command {
    private static final Logger LOGGER = Logger.getLogger(ToggleHatchSolenoid.class.getName());
    private IntakeSubsystem intakeSubsystem;

    /**
     * Instantiates the command
     */
    public ToggleHatchSolenoid() {
        this.intakeSubsystem = Robot.getIntakeSubsystem();
    }

    /**
     * Runs the command
     */
    @Override
    protected void execute() {
        intakeSubsystem.toggleSolenoidState();        
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
