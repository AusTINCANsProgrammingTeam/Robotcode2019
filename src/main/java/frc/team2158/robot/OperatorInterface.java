package frc.team2158.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @version 0.0.1
 * @author William Blount
 * Manages and Initializes buttons used in the controller.
 */
public class OperatorInterface {
    private static final Logger LOGGER = Logger.getLogger(OperatorInterface.class.getName());
    public enum ButtonMode {WHEN_PRESSED, CANCEL_WHEN_PRESSED, TOGGLE_WHEN_PRESSED, WHEN_RELEASED, WHILE_HELD}

    private Joystick driveController;
    private Joystick operatorController;
    private Map<String, Button> buttonMapDriver;
    private Map<String, Button> buttonMapOp;
    /**
     * Initializes the joystick and button map.
     *
     */
    public OperatorInterface() {
        // Initialize the joystick and button map.
        //Stretch Goal: Custom maps for different controllers?
        this.driveController = new Joystick(0);
        this.operatorController = new Joystick(1);
        this.buttonMapDriver = new HashMap<String, Button>() {{
            put("buttonA", new JoystickButton(driveController, 1));
            put("buttonB", new JoystickButton(driveController, 2));
            put("buttonX", new JoystickButton(driveController, 3));
            put("buttonY", new JoystickButton(driveController, 4));
            put("buttonLB", new JoystickButton(driveController, 5));
            put("buttonRB", new JoystickButton(driveController, 6));
            //put("buttonLT", new JoystickButton(driveController, 7));
            //put("buttonRT", new JoystickButton(driveController, 8));
            put("buttonBack", new JoystickButton(driveController, 7));
            put("buttonStart", new JoystickButton(driveController, 8));
            put("buttonLeftJoystick", new JoystickButton(driveController, 9));
            put("buttonRightJoystick", new JoystickButton(driveController, 10));
            put("buttonRightArrow", new JoystickButton(driveController, 0));
            //left joystick X axis is 0, Y axis 1
            //Right joy stick X axis is 4, Y axis 5
        }};
        this.buttonMapOp = new HashMap<String, Button>() {{
            put("button1", new JoystickButton(operatorController, 1));
            put("button2", new JoystickButton(operatorController, 2));
            put("button3", new JoystickButton(operatorController, 3));
            put("button4", new JoystickButton(operatorController, 4));
            put("buttonLB", new JoystickButton(operatorController, 5));
            put("buttonRB", new JoystickButton(operatorController, 6));
            put("buttonLT", new JoystickButton(operatorController, 7));
            put("buttonRT", new JoystickButton(operatorController, 8));
            put("buttonBack", new JoystickButton(operatorController, 9));
            put("buttonStart", new JoystickButton(operatorController, 10));
            put("buttonLeftJoystick", new JoystickButton(operatorController, 11));
            put("buttonRightJoystick", new JoystickButton(operatorController, 12));
            //left joystick X axis is 0, Y axis 1
            //Right joy stick X axis is 2, Y axis 3
        }};

    }

    /**
     * Easy way for us to bind buttons to a command.
     * @param button String representing a button that was registered by the Operator Interface
     * @param mode What state the button should be for command execution.
     * @param command What the button will trigger when the ButtonMode is triggered
     */
    public void bindButton(String button, ButtonMode mode, Command command, int joystickNum) {
        if(joystickNum == 0){
        switch(mode) {
            case WHEN_PRESSED:
                buttonMapDriver.get(button).whenPressed(command);
                break;
            case CANCEL_WHEN_PRESSED:
                buttonMapDriver.get(button).cancelWhenPressed(command);
                break;
            case TOGGLE_WHEN_PRESSED:
                buttonMapDriver.get(button).toggleWhenPressed(command);
                break;
            case WHEN_RELEASED:
                buttonMapDriver.get(button).whenReleased(command);
                break;
            case WHILE_HELD:
                buttonMapDriver.get(button).whileHeld(command);
                break;
        }
    }
        else if(joystickNum == 1){
            switch(mode) {
                case WHEN_PRESSED:
                    buttonMapOp.get(button).whenPressed(command);
                    break;
                case CANCEL_WHEN_PRESSED:
                    buttonMapOp.get(button).cancelWhenPressed(command);
                    break;
                case TOGGLE_WHEN_PRESSED:
                    buttonMapOp.get(button).toggleWhenPressed(command);
                    break;
                case WHEN_RELEASED:
                    buttonMapOp.get(button).whenReleased(command);
                    break;
                case WHILE_HELD:
                    buttonMapOp.get(button).whileHeld(command);
                    break;
            }
        }
    }

    /**
     * Returns the instance of the Joystick.
     * @return the instance of the Joystick.
     */
    public Joystick getDriveController() {
        return driveController;
    }
    
    public Joystick getOperatorController()
    {
        return operatorController;
    }
    /**
     * Returns the instance of the button named
     * @param name Name of the button
     * @return the instance of the button named
     */
    public Button getButtonByName(String name) {
        return buttonMapDriver.get(name);
    }
}
