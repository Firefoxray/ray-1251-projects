package org.usfirst.frc.team1251.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1251.robot.teleopInput.driverInput.HumanInput;

public class TeleopMoveArm extends Command {

    private final HumanInput humanInput;
    private final Arm arm;

    public TeleopMoveArm(HumanInput humanInput, Arm arm) {
        this.humanInput = humanInput;
        this.arm = arm;
        requires(this.arm);
    }

    @Override
    protected void execute() {
        double armUpSpeed = this.humanInput.getArmUpSpeed();
        double armDownSpeed = this.humanInput.getArmDownSpeed();

        SmartDashboard.putNumber("Arm speedUp", armUpSpeed);
        SmartDashboard.putNumber("Arm speedDown", armDownSpeed);

        if (armUpSpeed > 0) {
            this.arm.pivotUp(armUpSpeed);
        } else if (armDownSpeed > 0) {
            this.arm.pivotDown(armDownSpeed);
        } else {
            this.arm.stopPivot();
        }
    }

    @Override
    protected void end() {
        this.arm.stopPivot();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
