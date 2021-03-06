package org.usfirst.frc.team1251.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1251.robot.RobotMap;
import org.usfirst.frc.team1251.robot.virtualSensors.CrateDetector;

public class Collector extends Subsystem {

    private static final int MOTOR_LEFT_FORWARD = -1; //Gas Gas Gas
    private static final int MOTOR_RIGHT_FORWARD = 1; //Gotta step on the gas
    private static final int MOTOR_LEFT_BACKWARD = 1;
    private static final int MOTOR_RIGHT_BACKWARD = -1;
    private static final int MOTOR_STOP = 0; //Drift Button

    private static final double THROTTLED_COLLECT_SPEED = .40;


    // SAFETY!
    private static final double EJECTION_MAX_SPEED = 1.0;

    //The left bag motor, when looking from the rear perspective.
    private SpeedController leftMotor;

    //The right bag motor, when looking from the rear perspective.
    private SpeedController rightMotor;

    private CrateDetector crateDetector;

    public Collector(CrateDetector crateDetector) {
        this.crateDetector = crateDetector;
        this.leftMotor = new Victor(RobotMap.COLLECTOR_LEFT_MOTOR);
        this.rightMotor = new Victor(RobotMap.COLLECTOR_RIGHT_MOTOR);

        this.leftMotor.setInverted(true);
        this.rightMotor.setInverted(true);

    }

    @Override
    protected void initDefaultCommand() {

    }

    //Below includes Motor commands for collector victor's
    public void stop() {
        rightMotor.set(MOTOR_STOP);
        leftMotor.set(MOTOR_STOP);
    }

    public void pullIn() {

        if (!crateDetector.isCrateCollected()) {
            rightMotor.set(-MOTOR_RIGHT_FORWARD * 0.9);
            leftMotor.set(-MOTOR_LEFT_FORWARD * 0.9);
        } else {
            // Throttle intake when a crate is detected to protect the motors.
            rightMotor.set(-MOTOR_RIGHT_FORWARD * THROTTLED_COLLECT_SPEED);
            leftMotor.set(-MOTOR_LEFT_FORWARD * THROTTLED_COLLECT_SPEED);
        }

    }


    public void eject(double speed) {
        speed = Math.max(speed, 0);
        speed = Math.min(speed, EJECTION_MAX_SPEED);
        rightMotor.set(speed);
        leftMotor.set(-speed);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Collected? ", crateDetector.isCrateCollected());
    }
}