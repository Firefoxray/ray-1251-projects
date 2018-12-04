/***

package org.usfirst.frc.team1251.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1251.robot.RobotMap;
import org.usfirst.frc.team1251.robot.commands.DeferredCmdSupplier;
import org.usfirst.frc.team1251.robot.virtualSensors.ElevatorPosition;


public class Elevator extends Subsystem {

    // If the motors are inverted
    private static final boolean isMotor1Inverted = false;
    private static final boolean isMotor2Inverted = false;
    private final double SUSTAIN = 0.13;


    // Motor(s) for elevators (move it up and down)
    private Victor elevatorMotor1;
    private Victor elevatorMotor2;

    private ElevatorPosition elevatorPosition;
    private final DeferredCmdSupplier<Command> defaultCommand;

    public Elevator(ElevatorPosition elevatorPosition, DeferredCmdSupplier<Command> defaultCommand) {
        this.elevatorPosition = elevatorPosition;
        this.defaultCommand = defaultCommand;

        elevatorMotor1 = new Victor(RobotMap.ELEVATOR_MOTOR_1);
        elevatorMotor2 = new Victor(RobotMap.ELEVATOR_MOTOR_2);

        elevatorMotor1.setInverted(isMotor1Inverted);
        elevatorMotor2.setInverted(isMotor2Inverted);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Elevator Encoder", elevatorPosition.getTicks());
        SmartDashboard.putNumber("Elevator Height", elevatorPosition.getHeight());
        //System.out.println("HEIGHT: " + elevatorPosition.getTicks());
        SmartDashboard.putBoolean("Elevator Bottom", elevatorPosition.isAtMinHeight());

        if (elevatorPosition.isAtMinHeight()) {
            elevatorPosition.reset();
        }
    }


    public void goUp(double speed) {
        if (elevatorPosition.isAtMaxHeight()) {
            elevatorMotor1.set(SUSTAIN);
            elevatorMotor2.set(SUSTAIN);
            return;
        }

        // bounds speed to between 0 and 1
        speed = Math.max(speed, 0);
        speed = Math.min(speed, 1);

        if (elevatorPosition.isNearMaxHeight()) {
            speed = Math.min(speed, 0.4);
        }
        //speed = 0;

        elevatorMotor1.set(speed);
        elevatorMotor2.set(speed);
    }


    public void goDown(double speed) {
        // bounds speed to between 0 and 0.5
        speed = Math.max(speed, 0);
        speed = Math.min(speed, 0.8);
        speed *= -1;

        if (elevatorPosition.isAtMinHeight()) {
            speed = 0;
        }

        if (elevatorPosition.isNearBottom()) {
            speed = Math.min(speed, 0.3);
        }
        elevatorMotor1.set(speed);
        elevatorMotor2.set(speed);
    }


    public void move(double speed) {
        elevatorMotor1.set(speed);
        elevatorMotor2.set(speed);
    }



    public void stop() {
        elevatorMotor1.set(0);
        elevatorMotor2.set(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(this.defaultCommand.get());
    }
}
***/