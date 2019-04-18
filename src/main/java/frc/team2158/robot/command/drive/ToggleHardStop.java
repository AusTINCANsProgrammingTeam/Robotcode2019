package frc.team2158.robot.command.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2158.robot.Robot;

import java.util.logging.Logger;
/**
 * @author William Blount
 * @version 0.0.1
 * Switches the gear mode of the robot
 */
public class ToggleHardStop extends Command {
    private static final Logger LOGGER = Logger.getLogger(ToggleGearMode.class.getName());

    /**
     * Runs the command
     */
    @Override
    protected void initialize() {
        Robot.getStopSubsystem().toggleStopSolenoid();
    }

    @Override
    protected boolean isFinished() { return true; }
}
