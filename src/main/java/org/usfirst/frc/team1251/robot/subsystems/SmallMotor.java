package org.usfirst.frc.team1251.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1251.robot.RobotMap;
import org.usfirst.frc.team1251.robot.commands.DeferredCmdSupplier;

public class SmallMotor extends Subsystem {

    private static final boolean isMotorInverted = false;
    private final double SUSTAIN = 0.13;

    //Motors for Small SmallMotor
    private Victor smallMotor;

    private final DeferredCmdSupplier<Command> defaultCommand;

    public SmallMotor(DeferredCmdSupplier<Command> defaultCommand) {
        this.defaultCommand = defaultCommand;
        smallMotor = new Victor(RobotMap.SMALL_MOTOR);
        smallMotor.setInverted(isMotorInverted);
    }

    public void forward(double speed) {
        speed = Math.max(speed, 0);
        speed = Math.min(speed, 1);
        smallMotor.set(speed);
    }

    public void reverse(double speed) {
        speed = Math.max(speed, 0);
        speed = Math.min(speed, 0.8);
        speed *= -1;
    }

    public void stop() {
        smallMotor.set(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(this.defaultCommand.get());
    }
}
