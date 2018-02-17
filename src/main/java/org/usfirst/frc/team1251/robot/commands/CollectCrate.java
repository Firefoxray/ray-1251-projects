package org.usfirst.frc.team1251.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1251.robot.Collector;
import org.usfirst.frc.team1251.robot.Robot;
import org.usfirst.frc.team1251.robot.subsystems.Clawlector;
import org.usfirst.frc.team1251.robot.teleopInput.gamepad.GamePad;
import org.usfirst.frc.team1251.robot.virtualSensors.CrateDetector;

public class CollectCrate extends Command {

    private final GamePad gamePad;
    private final CrateDetector crateDetector;
    private final Clawlector clawlector;


    public CollectCrate()
    {
        this.gamePad = Robot.oi.driverPad;
        this.crateDetector = Robot.crateDetector;
        this.clawlector = Robot.CLAWLECTOR;
        this.requires(this.clawlector);
    }

    protected void execute() {
        //todo when press enable command
        if (this.crateDetector.getCrateState() == CrateDetector.CrateState.CRATE_COLLECTED)
        {
            System.out.println("Crate Collected");
            this.clawlector.getCollector().stop();
            return;
        }
        if (this.crateDetector.getCrateState() == CrateDetector.CrateState.SKEWED_LEFT)
        {
            System.out.println("Skewed Left");
            this.clawlector.getCollector().pullInLeft();
            return;
        }
        if (this.crateDetector.getCrateState() == CrateDetector.CrateState.DIAGONAL)
        {
            System.out.println("Diagonal");
            this.clawlector.getCollector().pullInLeft();
            return;
        }
        if (this.crateDetector.getCrateState() == CrateDetector.CrateState.SKEWED_RIGHT)
        {
            System.out.println("Skewed Right");
            this.clawlector.getCollector().pullInRight();
            return;
        }
        if (this.crateDetector.getCrateState() == CrateDetector.CrateState.NONE)
        {
            System.out.println("None");
            this.clawlector.getCollector().pullIn();
            return;
        }

    }
    protected void end()
    {
        this.clawlector.getCollector().stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
    //
}
