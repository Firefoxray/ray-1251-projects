package org.usfirst.frc.team1251.robot.virtualSensors;

import com.ctre.phoenix.motorcontrol.IMotorController;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.usfirst.frc.team1251.robot.MotorFactory;

public class DriveFeedback {


    /**
     * The main motor controller for the left side of the drive train.
     *
     * IMPORTANT: This is a sensor class -- it should ONLY be reading sensor data from this motor controller; it
     * should **NEVER** change the state of the motor controller!
     */
    private final IMotorController leftMasterMotor;

    /**
     * The main motor controller for the right side of the drive train.
     *
     * IMPORTANT: This is a sensor class -- it should ONLY be reading sensor data from this motor controller; it
     * should **NEVER** change the state of the motor controller!
     */
    private final IMotorController rightMasterMotor;

    /**
     * The main gyro for the bot.
     */
    private Gyro gyro;

    /**
     * The total distance traveled of the left side of drive train.
     */
    private int leftDistance;

    /**
     * The total distance traveled of the right side of drive train.
     */
    private int rightDistance;

    /**
     * The current velocity of the left drive train.
     */
    private int leftVelocity;

    /**
     * The current velocity of the right drive train.
     */
    private int rightVelocity;

    /**
     * Current heading of the bot.
     */
    private double angle;

    /**
     * Degrees/second turn rate.
     */
    private double turnRate;


    public DriveFeedback() {
        this.leftMasterMotor = MotorFactory.initLeftDriveMotors();
        this.rightMasterMotor = MotorFactory.initRightDriveMotors();

        this.gyro = new ADXRS450_Gyro();
    }



    /**
     * Ideally, this should be called exactly once per tick to get a representation of
     */
    public void updateSensorData() {
        this.leftDistance = leftMasterMotor.getSelectedSensorPosition(0);
        this.rightDistance = rightMasterMotor.getSelectedSensorPosition(0);

        this.leftVelocity = leftMasterMotor.getSelectedSensorVelocity(0);
        this.rightVelocity = rightMasterMotor.getSelectedSensorVelocity(0);

        this.angle = gyro.getAngle();
        this.turnRate = gyro.getRate();
    }

    public double getTurnRate() {
        return turnRate;
    }

    public double getAngle() {
        return angle;
    }

    public int getRightVelocity() {
        return rightVelocity;
    }

    public int getLeftVelocity() {
        return leftVelocity;
    }

    public int getRightDistance() {
        return rightDistance;
    }

    public int getLeftDistance() {
        return leftDistance;
    }
}
