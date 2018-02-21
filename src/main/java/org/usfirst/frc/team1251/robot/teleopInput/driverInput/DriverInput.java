package org.usfirst.frc.team1251.robot.teleopInput.driverInput;

import org.usfirst.frc.team1251.robot.commands.CollectCrate;
import org.usfirst.frc.team1251.robot.teleopInput.gamepad.GamePad;
import org.usfirst.frc.team1251.robot.teleopInput.triggers.GamePadButtonTrigger;

/**
 * This class translates driver input into easy-to-use values and command activations.
 *
 * All knowledge about which buttons/sticks do what is contained within this class -- no other code should be reading
 * directly from the driver input devices. By centralizing this knowledge, it becomes much easier to adjust the control
 * scheme since it is not scattered throughout the code base. This also uses "information hiding" to make sure that
 * the rest of the robot does care about the details of how driver input is interpreted.
 */
public class DriverInput {

    /**
     * The number of input samples to use for smoothing out wheel speed input.
     */
    private final static int WHEEL_SPEED_SMOOTHING_SAMPLES = 5;

    /**
     * The game pad which is used to move the robot around the field.
     */
    private final GamePad movementGamePad;

    /**
     * The game pad which is used to interact with the crates (e.g. "power cubes").
     */
    private final GamePad crateGamePad;

    /**
     * A utility for smoothing out the stick values used for setting the left wheel speed.
     */
    private final StickSmoothing leftWheelSmoothing;

    /**
     * A utility for smoothing out the stick values used for setting the right wheel speed.
     */
    private final StickSmoothing rightWheelSmoothing;

    /**
     * A game pad trigger which is used to activate the crate-collection command.
     */
    private final GamePadButtonTrigger collectCrateTrigger;

    /**
     *
     * @param movementGamePad The GamePad which is used to move the robot around the field.
     * @param crateGamePad The GamePad which is used to interact with creates (e.g. "power cubes")
     */
    public DriverInput(GamePad movementGamePad, GamePad crateGamePad) {
        this.movementGamePad = movementGamePad;
        this.crateGamePad = crateGamePad;

        // For the wheel speeds, we want to smooth out the stick values over the lat 5 samples.
        // The left and right sticks on the movement game pad are used for the left and right wheel speeds, respectively.
        this.leftWheelSmoothing = new StickSmoothing(
                this.movementGamePad.ls(), StickSmoothing.StickAxis.VERTICAL, WHEEL_SPEED_SMOOTHING_SAMPLES);
        this.rightWheelSmoothing = new StickSmoothing(
                this.movementGamePad.rs(),StickSmoothing.StickAxis.VERTICAL, WHEEL_SPEED_SMOOTHING_SAMPLES);

        // Use the right-bumper to trigger create collection.
        this.collectCrateTrigger = new GamePadButtonTrigger(this.crateGamePad.lb());
    }

    /**
     * Call this method to attach input triggers to actions.
     *
     * This method should only ever be called once.
     *
     * @param collectCrate The crate-collection Command.
     */
    public void attachCommandTriggers(CollectCrate collectCrate) {
        // TODO: Prevent duplicate bindings
        collectCrateTrigger.whileHeld(collectCrate);
    }

    /**
     * Get the driver-requested upward arm speed.
     *
     * @return A value between 0 and 1 representing the speed at which to move the arm where the speed increases as the
     *     value approaches 1.
     */
    public double getArmUpSpeed() {
        // TODO: Translate crateGamePad left stick vertical into arm speed.
        return 0;
    }

    /**
     * Get the driver-requested downward arm speed.
     *
     * @return A value between 0 and 1 representing the speed at which to move the arm where the speed increases as the
     *     value approaches 1.
     */
    public double getArmDownSpeed() {
        // TODO: Translate crateGamePad left stick vertical into arm speed.
        return 0;
    }

    /**
     * Get the driver-requested upward elevator speed.
     *
     * @return A value between 0 and 1 representing the speed at which to move the elevator where the speed increases
     *     as the value approaches 1.
     */
    public double getElevatorUpSpeed() {
        // TODO: Translate crateGamePad right stick vertical into elevator speed.
        return 0;
    }


    /**
     * Get the driver-requested downward elevator speed.
     *
     * @return A value between 0 and 1 representing the speed at which to move the elevator where the speed increases
     *     as the value approaches 1.
     */
    public double getElevatorDownSpeed() {
        // TODO: Translate crateGamePad right stick vertical into elevator speed.
        return 0;
    }

    /**
     * Get the driver-requested speed for the left wheel(s).
     *
     * @return A value between -1 and 1 representing the speed at which to move the wheel(s). Any value below 0
     *     represents backwards movement and any value above 0 represents forward movement. The speed forward or
     *     backward increases as the value approaches 1 or -1, respectively.
     */
    public double getLeftWheelSpeed() {
        // Return the smoothed out value.
        return this.leftWheelSmoothing.getSmoothedValue();
    }

    /**
     * Get the driver-requested speed for the left wheel(s).
     *
     * @return A value between -1 and 1 representing the speed at which to move the wheel(s). Any value below 0
     *     represents backwards movement and any value above 0 represents forward movement. The speed forward or
     *     backward increases as the value approaches 1 or -1, respectively.
     */
    public double getRightWheelSpeed() {
        // Return the smoothed out value.
        return this.rightWheelSmoothing.getSmoothedValue();
    }
}