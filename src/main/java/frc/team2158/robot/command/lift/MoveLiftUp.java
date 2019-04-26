package frc.team2158.robot.command.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.team2158.robot.Robot;
import frc.team2158.robot.subsystem.lift.Arm;
import frc.team2158.robot.subsystem.lift.Arm.Direction;

import java.util.logging.Logger;
/**
 * @author William Blount
 * @version 0.0.1
 * Changes the lift direction and speed.
 */
public class MoveLiftUp extends Command {
    private static final Logger LOGGER = Logger.getLogger(MoveLift.class.getName());
    private Arm.Direction direction = Direction.UP;
    private double speed;

    /**
     * Moves the lift by changing direction and speed
     * @param direction Either UP or DOWN
     * @param speed Speed of lift
     */
    public MoveLiftUp() {
        requires(Robot.getLiftSubsystem());
    }

    /**
     * Runs the command
     */
    @Override
    protected void initialize() {
        //Robot.getLiftSubsystem().moveLift(direction, speed);
    }
    /**
     * Runs the command
     */
    @Override
    protected void execute() {
        Robot.getLiftSubsystem().moveLiftPos(direction);;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
