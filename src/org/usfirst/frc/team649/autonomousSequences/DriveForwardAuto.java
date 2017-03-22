package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveForwardAuto extends CommandGroup {

    public DriveForwardAuto() {
	    addSequential(new DrivetrainPIDCommand(110, false)); // 85.375, 78.375, 70, 68, 70.5, 68, 69
    }
}
