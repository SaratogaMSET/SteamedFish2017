package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommadTimeoutBackup;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommandTurnTimeout;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommandWithTimeout;
import org.usfirst.frc.team649.robot.commands.ShiftDT;
import org.usfirst.frc.team649.robot.commands.SwitchDTMode;
import org.usfirst.frc.team649.shootercommands.FeedBallsToShooterCommand;
import org.usfirst.frc.team649.shootercommands.OnlyBangBangNoShootCommand;
import org.usfirst.frc.team649.shootercommands.TurretPIDABS;
import org.usfirst.frc.team649.util.GetShooterValues;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class BlueSideGearShootMiddleWithTimeout extends CommandGroup {

    public BlueSideGearShootMiddleWithTimeout() {
    	addSequential(new SwitchDTMode(true));
		addSequential(new ShiftDT(false));
	    addSequential(new SetGearFlap(true));
		addSequential(new SetFunnelCommand(false));
	    addSequential(new DrivetrainPIDCommandWithTimeout(70)); 
	    addSequential(new DrivetrainPIDCommandTurnTimeout(10));
	    addSequential(new DrivetrainPIDCommadTimeoutBackup(-15));
	    addSequential(new DrivetrainPIDCommadTimeoutBackup(13));
	    addSequential(new SetGearFlap(false));
	    addSequential(new WaitCommand(1));
	    addParallel(new TurretPIDABS(0.21*60));
	    addParallel(new OnlyBangBangNoShootCommand(2050,2150,1950,GetShooterValues.returnShooterMaxPower(2050),GetShooterValues.returnShooterMinPower(2050))); //1725, turret .98-.55 hood 0.625
		addSequential(new DrivetrainPIDCommand(-30, false));
		addSequential(new FeedBallsToShooterCommand(1.0));
    }
}
