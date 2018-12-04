package org.usfirst.frc.team1251.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team1251.robot.virtualSensors.DriveFeedback;

public class PIDTurn extends PIDCommand {

    private static final double P = 0.01;
    private static final double I = 0.000015;
    private static final double D = 0.0;


    enum Direction {
        CLOCKWISE, COUNTER_CLOCKWISE
    }

    private static final double PERIOD = 0.01;

    //private DriveTrain driveTrain;
    private DriveFeedback driveFeedback;
    private double desiredAngle;
    private Direction direction;


    public PIDTurn(DriveFeedback driveFeedback, double desiredAngle) {
        super(P, I, D, PERIOD);

        this.desiredAngle = desiredAngle;
        //this.driveTrain = driveTrain;
        this.driveFeedback = driveFeedback;
        this.setInputRange(0.0, 360.0);
        this.getPIDController().setAbsoluteTolerance(1.0);
        this.getPIDController().setContinuous();
    }


    @Override
    protected void initialize() {
        double distanceToTarget = distanceToTarget();
        this.getPIDController().setSetpoint(desiredAngle);

        if (distanceToTarget < 0) {
            direction = Direction.COUNTER_CLOCKWISE;
        } else {
            direction = Direction.CLOCKWISE;
        }
    }

    @Override
    protected double returnPIDInput() {
        driveFeedback.updateSensorData();
        return driveFeedback.getHeading();
    }

    @Override
    protected void usePIDOutput(double output) {

    }

    /**
    @Override
    protected void usePIDOutput(double output) {
        if (direction == Direction.COUNTER_CLOCKWISE) {
            driveTrain.setSpeed(-output, output);
        } else {
            driveTrain.setSpeed(output, -output);
        }
    **/

    private double distanceToTarget() {
        driveFeedback.updateSensorData();
        double heading = driveFeedback.getHeading();
        if (heading < desiredAngle) {
            heading += 360;
        }

        double left = heading - desiredAngle;

        if (left < 180)
            return -left;
        else {
            return 360 - left;
        }
    }

    @Override
    protected boolean isFinished() {
        return getPIDController().onTarget();
    }
}
