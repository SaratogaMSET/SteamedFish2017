package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.commandgroups.DumpGearCommandGroup;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.intakecommands.SetIntakeWedgePistons;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.shootercommands.FeedBallsToShooterCommand;
import org.usfirst.frc.team649.shootercommands.OnlyBangBangNoShootCommand;
import org.usfirst.frc.team649.shootercommands.SetHoodCommand;
import org.usfirst.frc.team649.shootercommands.TurretPID;
import org.usfirst.frc.team649.shootercommands.TurretPIDABS;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RedSideGearShootMiddle extends CommandGroup {

    public RedSideGearShootMiddle() {
         
    	addSequential(new SetGearFlap(true));
		addSequential(new SetIntakeWedgePistons(false));
		addSequential(new SetFunnelCommand(false));
	    addSequential(new DrivetrainPIDCommand(72, false)); // 85.375, 78.375, 70, 68, 70.5, 68, 69
	    addSequential(new SetGearFlap(false));
	    addSequential(new WaitCommand(1));
	    addParallel(new TurretPIDABS(155.5));
		addParallel(new SetHoodCommand(40)); //temporary
	    addParallel(new OnlyBangBangNoShootCommand(1600,1750,1350,0.62,0.58));
		addSequential(new DrivetrainPIDCommand(-30, false));
		addSequential(new FeedBallsToShooterCommand(1.0));

    }
}
