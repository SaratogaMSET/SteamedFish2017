package org.usfirst.frc.team649.autonomousSequences;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
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
public class RedSideBoilerNoGearShoot extends CommandGroup {

    public RedSideBoilerNoGearShoot() {

    	addSequential(new SwitchDTMode(true));
    	addSequential(new ShiftDT(false));
    	addSequential(new SetGearFlap(true));
		addSequential(new SetFunnelCommand(false));
		addSequential(new DrivetrainPIDCommand(82, false));
	    addSequential(new DrivetrainPIDCommand(-65.5, true));
	    addSequential(new DrivetrainPIDCommand(38.5,false));  
	    addParallel(new TurretPIDABS(180-60*2.17));
	    addParallel(new SetHoodCommand(0.17)); 
	    addParallel(new OnlyBangBangNoShootCommand(1450,1650,1250,GetShooterValues.returnShooterMaxPower(1450),GetShooterValues.returnShooterMinPower(1450))); //1425 hood:0.1406 turret: 2.17
		addSequential(new FeedBallsToShooterCommand(1.0));
		
		

    }
}
