package frc.team2158.robot.command.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2158.robot.Robot;

import java.util.logging.Logger;
/**
 * @author William Blount
 * @version 0.0.1
 * Runs the command in order to stop the lift
 */
public class StopSelfLift extends Command {
    private static final Logger LOGGER = Logger.getLogger(StopLift.class.getName());

    /**
     * Instantiates the command.
     */
    public StopSelfLift() {
        requires(Robot.getSelfLiftSubsystem());
    }
    /**
     * Runs the command.
     */
    @Override
    protected void initialize() {
        Robot.getSelfLiftSubsystem().stopLift();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
