package frc.team2158.robot.command.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2158.robot.Robot;
import frc.team2158.robot.subsystem.drive.StopSubsystem;
import frc.team2158.robot.subsystem.drive.StopSubsystem.StopDirection;

import java.util.logging.Logger;
public class HardStop extends Command {
    StopDirection direction = StopDirection.UP;
    private static final Logger LOGGER = Logger.getLogger(HardStop.class.getName());

    /**
     * Runs the command
     */
    @Override
    protected void initialize() {
        //Robot.getStopSubsystem().raiseStop(direction);
        //LOGGER.info(String.format("GearMode has been toggled to %s!", Robot.getDriveSubsystem().getGearMode()));
    }

    @Override
    protected boolean isFinished() { return true; }
}