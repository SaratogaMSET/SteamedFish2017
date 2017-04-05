package org.usfirst.frc.team649.commandgroups;

import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class GearRetry extends CommandGroup {

    public GearRetry() {
        addSequential(new DrivetrainPIDCommand(-15, false));
        addSequential(new WaitCommand(0.2));
        addSequential(new DrivetrainPIDCommand(15, false));
    }
}
