package org.usfirst.frc.team649.autonomousSequences;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommandWithTimeout;
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
public class RedSideBoilerGearShoot extends CommandGroup {

    public RedSideBoilerGearShoot() {

    	addSequential(new SwitchDTMode(true));
    	addSequential(new ShiftDT(false));
    	addSequential(new SetGearFlap(true));
		addSequential(new SetFunnelCommand(false));
		addSequential(new DrivetrainPIDCommand(71, false));
	    addSequential(new DrivetrainPIDCommand(-60, true));
	    addSequential(new DrivetrainPIDCommand(62,false));  
	    addSequential(new SetGearFlap(false));
	    addSequential(new WaitCommand(0.5));
	    addParallel(new TurretPIDABS(180-60*2.03));
	    addParallel(new SetHoodCommand(0.14)); 
	    addParallel(new OnlyBangBangNoShootCommand(1375,1475,1275,GetShooterValues.returnShooterMaxPower(1375),GetShooterValues.returnShooterMinPower(1375))); //1425 hood:0.1406 turret: 2.17
		addSequential(new DrivetrainPIDCommand(-20, false));
		addSequential(new FeedBallsToShooterCommand(1.0));
		
		

    }
}
