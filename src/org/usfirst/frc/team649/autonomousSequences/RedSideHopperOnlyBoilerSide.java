package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.gearcommands.SetFunnelFlywheels;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.robot.commands.DriveForTime;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.ShiftDT;
import org.usfirst.frc.team649.robot.commands.SwitchDTMode;
import org.usfirst.frc.team649.robot.commands.TurnWithEncoders;
import org.usfirst.frc.team649.shootercommands.FeedBallsToShooterCommand;
import org.usfirst.frc.team649.shootercommands.OnlyBangBangNoShootCommand;
import org.usfirst.frc.team649.shootercommands.SetHoodCommand;
import org.usfirst.frc.team649.shootercommands.TurretPID;
import org.usfirst.frc.team649.shootercommands.TurretPIDABS;
import org.usfirst.frc.team649.util.GetShooterValues;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RedSideHopperOnlyBoilerSide extends CommandGroup {

    public RedSideHopperOnlyBoilerSide() {
    	addSequential(new SwitchDTMode(true));
    	addSequential(new ShiftDT(false));
    	addSequential(new DrivetrainPIDCommand(80, false));
    	addSequential(new DrivetrainPIDCommand(-94.5, true));//addSequential(new TurnWithEncoders(90));
    	addParallel(new SetFunnelCommand(true));
    	addParallel(new TurretPIDABS(180));
    	addParallel(new SetFunnelFlywheels(1));
    	addSequential(new DrivetrainPIDCommand(54, false));
    	addSequential(new DriveForTime(0.1));
	    addParallel(new OnlyBangBangNoShootCommand(1450,1650,1250,GetShooterValues.returnShooterMaxPower(1450),GetShooterValues.returnShooterMinPower(1450)));
		addSequential(new FeedBallsToShooterCommand(1.0));
    }
}
