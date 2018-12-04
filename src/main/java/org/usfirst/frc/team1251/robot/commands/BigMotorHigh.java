package org.usfirst.frc.team1251.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1251.robot.subsystems.BigMotor;

public class BigMotorHigh extends Command {

    private final BigMotor bigMotor;

    public BigMotorHigh(BigMotor bigMotor) {

        this.bigMotor = bigMotor;
        requires(bigMotor);
    }

    @Override
    protected void execute() {
        this.bigMotor.closeClaw();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
