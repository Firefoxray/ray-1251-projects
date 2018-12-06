package org.usfirst.frc.team1251.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1251.robot.RobotMap;
import org.usfirst.frc.team1251.robot.commands.DeferredCmdSupplier;

public class Gearbox extends Subsystem {

    //If Motors are inverted
    private static final boolean isMotor1Inverted = false;
    private static final boolean isMotor2Inverted = false;
    private final double SUSTAIN = 0.13;

    //Motors for Gearbox
    private Victor gearboxMotor1;
    private Victor gearboxMotor2;

    private final DeferredCmdSupplier<Command> defaultCommand;

    public Gearbox(DeferredCmdSupplier<Command> defaultCommand)
    {
        this.defaultCommand = defaultCommand;

        gearboxMotor1 = new Victor(RobotMap.GEARBOX_MOTOR_1);
        gearboxMotor2 = new Victor(RobotMap.GEARBOX_MOTOR_2);

        gearboxMotor1.setInverted(isMotor1Inverted);
        gearboxMotor2.setInverted(isMotor2Inverted);
    }

    public void forward(double speed){
        speed = Math.max(speed, 0);
        speed = Math.min(speed, 1);

        gearboxMotor1.set(speed);
        gearboxMotor2.set(speed);

    }

    public void reverse(double speed){
        speed = Math.max(speed, 0);
        speed = Math.min(speed, 0.8);
        speed *= -1;

        gearboxMotor1.set(speed);
        gearboxMotor2.set(speed);
    }

    public void stop() {
        gearboxMotor1.set(0);
        gearboxMotor2.set(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(this.defaultCommand.get());
    }
}
