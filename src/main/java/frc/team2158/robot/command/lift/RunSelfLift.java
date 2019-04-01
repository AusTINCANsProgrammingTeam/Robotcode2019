package frc.team2158.robot.command.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2158.robot.Robot;
import frc.team2158.robot.subsystem.lift.Arm;

import java.util.logging.Logger;
/**
 * @author William Blount
 * @version 0.0.1
 * Changes the lift direction and speed.
 */
public class RunSelfLift extends Command {
    private static final Logger LOGGER = Logger.getLogger(MoveLift.class.getName());
    private Arm.Direction direction;
    private double speed;

    /**
     * Moves the lift by changing direction and speed
     * @param direction Either UP or DOWN
     * @param speed Speed of lift
     */
    public RunSelfLift() {
        requires(Robot.getSelfLiftSubsystem());
    }

    /**
     * Runs the command
     */
    @Override
    protected void initialize() {
    }
    /**
     * Runs the command
     */
    @Override
    protected void execute() {
        Robot.getSelfLiftSubsystem().selfLift();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
