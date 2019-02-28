package frc.team2158.robot.subsystem.drive;

import edu.wpi.first.wpilibj.SpeedController;

import com.revrobotics.CANSparkMax;

import java.util.logging.Logger;

public class SparkMaxGroup implements SpeedController {
    private static final Logger LOGGER = Logger.getLogger(SparkMaxGroup.class.getName());
    private CANSparkMax master;


    /**
     *
     * Creates a new TalonSRXGroup from a set of WPI_TalonSRX instances. The first instance is used as the master.
     * @param master the master motor. All other motors will follow this one.
     * @param slaves the slave motors. Follow the master motor.
     */
    public SparkMaxGroup(CANSparkMax master, CANSparkMax... slaves) {
        this.master = master;

        //master.configContinuousCurrentLimit(20, 0);
        //master.configPeakCurrentLimit(30, 0);
        //master.configPeakCurrentDuration(250, 0);
        //master.enableCurrentLimit(true);
        //master.configOpenloopRamp(0.250, 0);
        //master.configClosedloopRamp(0.250, 0);
        //master.config_kP(0, 0.4, 0);
        //master.config_kI(0, 0.0, 0);
        //master.config_kD(0, 0.0, 0);
        master.restoreFactoryDefaults();
        for(CANSparkMax slave : slaves) {
            slave.follow(master);
            slave.restoreFactoryDefaults();
        }
    }

    /**
     * Set the output to the value calculated by PIDController.
     * @param output the value calculated by PIDController.
     */
    @Override
    public void pidWrite(double output) {
        master.pidWrite(output);
    }

    /**
     * Common interface for setting the speed of a speed controller.
     * @param speed the speed to set. Value should be between -1.0 and 1.0.
     */
    @Override
    public void set(double speed) {
        master.set(speed);
    }

    /**
     * Common interface for getting the current set speed of the speed controller.
     * @return the current set speed. Value is between -1.0 and 1.0.
     */
    @Override
    public double get() {
        return master.get();
    }

    /**
     * Common interface for inverting direction of a speed controller.
     * @param isInverted the state of inversion; true is inverted.
     */
    @Override
    public void setInverted(boolean isInverted) {
        master.setInverted(isInverted);
    }

    /**
     * Common interface for returning if a speed controller is in the inverted state or not.
     * @return the state of the inversion; true is inverted.
     */
    @Override
    public boolean getInverted() {
        return master.getInverted();
    }

    /**
     * Disable the speed controller.
     */
    @Override
    public void disable() {
        master.disable();
    }

    /**
     * Stops motor movement. Motor can be moved again by calling set without having to re-enable the motor.
     */
    @Override
    public void stopMotor() {
        master.stopMotor();
    }
}