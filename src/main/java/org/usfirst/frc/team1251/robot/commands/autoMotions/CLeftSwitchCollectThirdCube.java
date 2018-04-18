package org.usfirst.frc.team1251.robot.commands.autoMotions;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1251.robot.commands.MotionProfiling.FollowPath;
import org.usfirst.frc.team1251.robot.subsystems.Arm;
import org.usfirst.frc.team1251.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1251.robot.subsystems.Elevator;
import org.usfirst.frc.team1251.robot.virtualSensors.ArmPosition;
import org.usfirst.frc.team1251.robot.virtualSensors.DriveFeedback;

public class CLeftSwitchCollectThirdCube extends CommandGroup {


    public CLeftSwitchCollectThirdCube(DriveFeedback driveFeedback, DriveTrain driveTrain,
                                       Arm arm, ArmPosition armPosition,
                                       Elevator elevator) {

        // After .5 second delay, start moving armevator into position for floor collection
        // TODO: Implement as parallel

        // Move away from switch
        addSequential(new ReverseFromSwitch(driveTrain, driveFeedback));

    }

    private class ReverseFromSwitch extends FollowPath {

        ReverseFromSwitch(DriveTrain driveTrain, DriveFeedback driveFeedback) {
            super("CLeftSwitch_CubeAll_LeaveSwitch", driveTrain, driveFeedback);
        }
    }
}