package org.usfirst.frc.team1251.robot.virtualSensors;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc.team1251.robot.RobotMap;

public class ElevatorPosition {
    public static final int NEAR_MAX_THRESHOLD = 900;
    private Encoder elevatorEncoder;
    private DigitalInput bottomSwitch;
    private final double GEAR_RATIO = 3.21428571428;
    private final double TICKS_PER_GEAR_TURN = 500 * GEAR_RATIO; // total apples
    private final double GEAR_CIRCUMFERENCE = Math.PI; // Inches per turn | # of buckets
    private final double TICKS_PER_INCH = TICKS_PER_GEAR_TURN / GEAR_CIRCUMFERENCE;

    private final static double MAX_HEIGHT = 4000; // in ticks

    private final static boolean NORMALLY_OFF = false;

    // Constructor
    public ElevatorPosition(){
        elevatorEncoder = new Encoder(RobotMap.ELEVATOR_ENCODER_CHANNEL_A, RobotMap.ELEVATOR_ENCODER_CHANNEL_B, false, CounterBase.EncodingType.k4X);
        elevatorEncoder.reset();
        bottomSwitch = new DigitalInput(RobotMap.ELEVATOR_BOTTOM_LIMIT_SWITCH);
    }

    public int getTicks() {
        return elevatorEncoder.get();
    }

    // Gets height in inches through conversion of Encoder Ticks to inches
    // Currently there is something wrong with the math.
    // TODO: Fix elevator math
    public double getHeight(){
        double encoderTicks = (double) elevatorEncoder.get();
        return encoderTicks / TICKS_PER_INCH;
    }

    public boolean isAtMaxHeight() {
        return getTicks() >= MAX_HEIGHT;
    }

    public boolean isAtMinHeight() {
        return bottomSwitch.get() == NORMALLY_OFF;
    }

    public void reset() {
        elevatorEncoder.reset();
    }

    public boolean isNearMaxHeight(){
        return getTicks() >= (MAX_HEIGHT-NEAR_MAX_THRESHOLD);
    }

    public boolean isNearBottom() {
        return getTicks() <= 500;
    }
}