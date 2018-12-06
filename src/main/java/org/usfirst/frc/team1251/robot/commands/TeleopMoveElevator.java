package org.usfirst.frc.team1251.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1251.robot.teleopInput.driverInput.HumanInput;

public class TeleopMoveElevator extends Command {


    private final HumanInput humanInput;
    private final Elevator elevator;

    public TeleopMoveElevator(HumanInput humanInput, Elevator elevator) {
        this.humanInput = humanInput;
        this.elevator = elevator;
        this.requires(this.elevator);
    }

    @Override
    protected void execute() {
        double elevatorUpSpeed = this.humanInput.getElevatorUpSpeed();
        double elevatorDownSpeed = this.humanInput.getElevatorDownSpeed();

        if (elevatorUpSpeed > 0) {
            this.elevator.goUp(elevatorUpSpeed);
        } else if (elevatorDownSpeed > 0) {
            this.elevator.goDown(elevatorDownSpeed);
        } else {
            this.elevator.stop();
        }
    }

    @Override
    public void end() {
        this.elevator.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
