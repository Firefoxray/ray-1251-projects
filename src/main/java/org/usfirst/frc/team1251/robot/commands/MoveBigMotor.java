package org.usfirst.frc.team1251.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1251.robot.subsystems.BigMotor;
import org.usfirst.frc.team1251.robot.teleopInput.driverInput.HumanInput;

public class MoveBigMotor extends Command{

    private final HumanInput humanInput;
    private final BigMotor bigMotor;

    public MoveBigMotor(HumanInput humanInput, BigMotor bigMotor)
    {
        this.humanInput = humanInput;
        this.bigMotor = bigMotor;
        requires(this.bigMotor);
    }

    @Override
    protected void execute() {
        double bigMotorForwardSpeed = this.humanInput.getBigMotorForwardSpeed();
        double bigMotorReverseSpeed = this.humanInput.getBigMotorReverseSpeed();

        if (bigMotorForwardSpeed > 0) {
            this.bigMotor.forward(bigMotorForwardSpeed);
        } else if (bigMotorReverseSpeed > 0) {
            this.bigMotor.reverse(bigMotorReverseSpeed);
        } else {
            this.bigMotor.stop();
        }
    }

    @Override
    protected void end() {
        this.bigMotor.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
