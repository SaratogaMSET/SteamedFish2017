package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.intakecommands.SetIntakeWedgePistons;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
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
		addSequential(new SetIntakeWedgePistons(false));
		addSequential(new SetFunnelCommand(false));
	    addSequential(new DrivetrainPIDCommand(82, false)); // 85.375, 78.375, 70, 68, 70.5, 68, 69
	    addSequential(new DrivetrainPIDCommand(65.5, true));
	    addSequential(new DrivetrainPIDCommand(58.5,false));  
	    addSequential(new SetGearFlap(false));
	    addSequential(new WaitCommand(0.5));
	    addParallel(new TurretPIDABS(60*2.17));
	    addParallel(new SetHoodCommand(0.1406)); //temporary
	    addParallel(new OnlyBangBangNoShootCommand(1425,1625,1225,GetShooterValues.returnShooterMaxPower(1425),GetShooterValues.returnShooterMinPower(1425))); //1425 hood:0.1406 turret: 2.17
		addSequential(new DrivetrainPIDCommand(-50, false));
		addSequential(new FeedBallsToShooterCommand(1.0));

    }
}
