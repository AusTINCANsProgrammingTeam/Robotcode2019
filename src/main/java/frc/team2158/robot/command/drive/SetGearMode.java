package frc.team2158.robot.command.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2158.robot.Robot;

import java.util.logging.Logger;

/**
 * @author William Blount
 * @version 0.0.1
 * Changes the Gear mode either to LOW or HIGH
 */
public class SetGearMode extends Command {
    private static final Logger LOGGER = Logger.getLogger(SetGearMode.class.getName());

    
    public SetGearMode() {
    }

    /**
     * Runs the command
     */
    @Override
    protected void initialize() {
        Robot.getDriveSubsystem().toggleGearMode();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
