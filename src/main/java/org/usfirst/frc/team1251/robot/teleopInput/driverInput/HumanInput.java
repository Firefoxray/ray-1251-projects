package org.usfirst.frc.team1251.robot.teleopInput.driverInput;

import org.usfirst.frc.team1251.robot.commands.*;
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
public class HumanInput {
    private static final double ELEVATOR_DEAD_ZONE = 0.1;
    private static final double ARM_DEAD_ZONE = 0.1;
    private final GamePadButtonTrigger PistonOutTrigger;
    private final GamePadButtonTrigger PistonInTrigger;
    private final GamePadButtonTrigger BigMotorHighTrigger;
    private final GamePadButtonTrigger BigMotorLowTrigger;

    private boolean commandTriggersAttached = false;

    /**
     * The number of input samples to use for smoothing out wheel speed input.
     */
    private final static int WHEEL_SPEED_SMOOTHING_SAMPLES = 1;

    /**
     * The game pad which is used to move the robot around the field.
     *
     */
    public final GamePad driverGamePad;

    /**
     * The game pad which is used to interact with the crates (e.g. "power cubes").
     */
    public final GamePad operatorGamePad;

    /**
     * A utility for smoothing out the stick values used for setting the left wheel speed.
     */
    private final StickSmoothing leftWheelSmoothing;

    /**
     * A utility for smoothing out the stick values used for setting the right wheel speed.
     */
    private final StickSmoothing rightWheelSmoothing;


    /**
     *
     * @param driverGamePad The GamePad which is used to move the robot around the field.
     * @param operatorGamePad The GamePad which is used to interact with creates (e.g. "power cubes")
     */
    public HumanInput(GamePad driverGamePad, GamePad operatorGamePad) {
        this.driverGamePad = driverGamePad;
        this.operatorGamePad = operatorGamePad;

        // For the wheel speeds, we want to smooth out the stick values over the lat 5 samples.
        // The left and right sticks on the movement game pad are used for the left and right wheel speeds, respectively.
        this.leftWheelSmoothing = new StickSmoothing(
                this.driverGamePad.ls(), StickSmoothing.StickAxis.VERTICAL, WHEEL_SPEED_SMOOTHING_SAMPLES);
        this.rightWheelSmoothing = new StickSmoothing(
                this.driverGamePad.rs(),StickSmoothing.StickAxis.VERTICAL, WHEEL_SPEED_SMOOTHING_SAMPLES);

        // Use the right-bumper to trigger create collection.
        this.BigMotorHighTrigger = new GamePadButtonTrigger(this.operatorGamePad.x());
        this.BigMotorLowTrigger = new GamePadButtonTrigger(this.operatorGamePad.a());

        this.PistonOutTrigger = new GamePadButtonTrigger(this.operatorGamePad.y());
        this.PistonInTrigger = new GamePadButtonTrigger(this.operatorGamePad.b());
    }

    /**
     * Call this method to attach input triggers to actions.
     *
     * This method should only ever be called once.
     *
     *
     */
    public void attachCommandTriggers(ShiftElevator shiftPistonOut,
                                      ShiftElevator shiftPistonIn,
                                      BigMotorLow bigMotorLow,
                                      BigMotorHigh bigMotorHigh) {
        // Prevent duplicate bindings.
        if (commandTriggersAttached) {
            return;
        }
        commandTriggersAttached = true;

        // Bind buttons.

        PistonOutTrigger.whileHeld(shiftPistonOut);
        PistonInTrigger.whileHeld(shiftPistonIn);

        BigMotorHighTrigger.whenPressed(bigMotorLow);
        BigMotorLowTrigger.whenPressed(bigMotorHigh);

    }

    /**
     * Get the driver-requested upward arm speed.
     *
     * @return A value between 0 and 1 representing the speed at which to move the arm where the speed increases as the
     *     value approaches 1.
     */
    public double getArmUpSpeed() {
        double armStick = operatorGamePad.rs().getVertical(ARM_DEAD_ZONE);
        if (armStick > 0){
            return armStick;
        } else {
            return 0;
        }
    }

    /**
     * Get the driver-requested downward arm speed.
     *
     * @return A value between 0 and 1 representing the speed at which to move the arm where the speed increases as the
     *     value approaches 1.
     */
    public double getArmDownSpeed() {
        double armStick = operatorGamePad.rs().getVertical(ARM_DEAD_ZONE);
        if (armStick < 0){
            return Math.abs(armStick);
        } else {
            return 0;
        }
    }

    /**
     * Get the driver-requested upward elevator speed.
     *
     * @return A value between 0 and 1 representing the speed at which to move the elevator where the speed increases as the value approaches 1.
     *
     */
    public double getBigMotorForwardSpeed() {
        double elevatorStick = operatorGamePad.ls().getVertical(ELEVATOR_DEAD_ZONE);
        if (elevatorStick > 0){
            return elevatorStick;
        } else {
            return 0;
        }
    }

    /**
     * Get the driver-requested downward elevator speed.
     *
     * @return A value between 0 and 1 representing the speed at which to move the elevator where the speed increases
     *     as the value approaches 1.
     */
    public double getBigMotorReverseSpeed() {
        double elevatorStick = operatorGamePad.ls().getVertical(ELEVATOR_DEAD_ZONE);
        if (elevatorStick < 0){
            return Math.abs(elevatorStick);
        } else {
            return 0;
        }
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

    public void rumbleOperator(double rumble)
    {
        this.operatorGamePad.rumbleLeft(rumble);
        this.operatorGamePad.rumbleRight(rumble);
    }
    public void rumbleDriver(double rumble)
    {
        this.driverGamePad.rumbleLeft(rumble);
        this.driverGamePad.rumbleRight(rumble);
    }

}