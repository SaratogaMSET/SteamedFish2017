package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommadTimeoutBackup;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommandTurnTimeout;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommandWithTimeout;
import org.usfirst.frc.team649.robot.commands.GyroTurnPID;
import org.usfirst.frc.team649.robot.commands.ShiftDT;
import org.usfirst.frc.team649.robot.commands.SwitchDTMode;
import org.usfirst.frc.team649.shootercommands.FeedBallsToShooterCommand;
import org.usfirst.frc.team649.shootercommands.OnlyBangBangNoShootCommand;
import org.usfirst.frc.team649.shootercommands.SetHoodCommand;
import org.usfirst.frc.team649.shootercommands.TurretPIDABS;
import org.usfirst.frc.team649.util.GetShooterValues;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class BlueSideBoilerGearShoot extends CommandGroup {

    public BlueSideBoilerGearShoot() {
    	addSequential(new SwitchDTMode(true));
    	addSequential(new ShiftDT(false));
    	addSequential(new SetGearFlap(true));
		addSequential(new SetFunnelCommand(false));
	    addSequential(new DrivetrainPIDCommand(71, false));
//	    addSequential(new DrivetrainPIDCommand(60, true));
	    addSequential(new GyroTurnPID(56));
	    addSequential(new DrivetrainPIDCommandWithTimeout(62)); 
	    addSequential(new DrivetrainPIDCommandTurnTimeout(10));
	    addSequential(new DrivetrainPIDCommadTimeoutBackup(-15));
	    addSequential(new DrivetrainPIDCommadTimeoutBackup(13));
	    addSequential(new SetGearFlap(false));
	    addSequential(new WaitCommand(0.5));
	    addParallel(new TurretPIDABS(60*1.925));
	    addParallel(new OnlyBangBangNoShootCommand(1425,1525,1325,GetShooterValues.returnShooterMaxPower(1425),GetShooterValues.returnShooterMinPower(1425))); //1425 hood:0.1406 turret: 2.17
		addSequential(new DrivetrainPIDCommand(-50, false));
		addSequential(new FeedBallsToShooterCommand(1.0));

    }
}
