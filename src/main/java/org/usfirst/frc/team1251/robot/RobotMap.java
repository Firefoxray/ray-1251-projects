package org.usfirst.frc.team1251.robot;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 *
 *
 *        MOTOR POSITIONS
 *
 *          ---   ---         F
 *         | L | | 1 |        R
 *          ---   ---         O
 *     ---            ---     N
 *    | 2 |          | 3 |    T
 *     ---            ---
 *
 *`
 */
public class RobotMap {
    private static class PwmDevices {
        static final int MOTOR_GEARBOX_1 = 0;
        static final int MOTOR_GEARBOX_2 = 1;
        static final int MOTOR_SMALL = 2;
    }

    private static class PcmDevices {
        static final int SOLENOID_HIGH_GEARBOX_SHIFTER = 0;
        static final int SOLENOID_LOW_GEARBOX_SHIFTER = 1;

        static final int SOLENOID_OUT_PISTON = 2;
        static final int SOLENOID_IN_PISTON = 3;

    }

    //NEW Public access to ids
    public static final int GEARBOX_MOTOR_1 = PwmDevices.MOTOR_GEARBOX_1;
    public static final int GEARBOX_MOTOR_2 = PwmDevices.MOTOR_GEARBOX_2;
    public static final int SMALL_MOTOR = PwmDevices.MOTOR_SMALL;

    public static final int GEARBOX_SHIFTER_HIGH = PcmDevices.SOLENOID_HIGH_GEARBOX_SHIFTER;
    public static final int GEARBOX_SHIFTER_LOW = PcmDevices.SOLENOID_LOW_GEARBOX_SHIFTER;

    public static final int PISTON_OUT = PcmDevices.SOLENOID_OUT_PISTON;
    public static final int PISTON_IN = PcmDevices.SOLENOID_IN_PISTON;
}
