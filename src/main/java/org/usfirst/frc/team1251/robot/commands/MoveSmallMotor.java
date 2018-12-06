package org.usfirst.frc.team1251.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1251.robot.subsystems.SmallMotor;
import org.usfirst.frc.team1251.robot.teleopInput.driverInput.HumanInput;

public class MoveSmallMotor extends Command {

    private final HumanInput humanInput;
    private final SmallMotor smallMotor;

    public MoveSmallMotor(HumanInput humanInput, SmallMotor smallMotor){
        this.humanInput = humanInput;
        this.smallMotor = smallMotor;
        requires(this.smallMotor);
    }

    @Override
    protected void execute(){

    }

    @Override
    public void end() {
        this.smallMotor.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
