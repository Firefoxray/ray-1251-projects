package org.usfirst.frc.team1251.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1251.robot.RobotMap;
import org.usfirst.frc.team1251.robot.commands.DeferredCmdSupplier;

public class GearboxShifter extends DoubleSolenoidGearShifter {

    public GearboxShifter(DeferredCmdSupplier<Command> defaultCommand)
    {
        super(defaultCommand);
        this.isInverted = false;
        this.solenoid = new DoubleSolenoid(RobotMap.GEARBOX_SHIFTER_HIGH, RobotMap.GEARBOX_SHIFTER_LOW);
    }
}
