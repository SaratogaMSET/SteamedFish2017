package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommandWithTimeout;
import org.usfirst.frc.team649.robot.commands.ShiftDT;
import org.usfirst.frc.team649.robot.commands.SwitchDTMode;
import org.usfirst.frc.team649.robot.commands.TurnWithEncoders;
import org.usfirst.frc.team649.shootercommands.OnlyBangBangNoShootCommand;
import org.usfirst.frc.team649.shootercommands.SetHoodCommand;
import org.usfirst.frc.team649.shootercommands.TurretPID;
import org.usfirst.frc.team649.shootercommands.TurretPIDABS;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class BlueSideGearFarSide extends CommandGroup {

    public BlueSideGearFarSide() {
    
    	addSequential(new SwitchDTMode(true));
    	addSequential(new ShiftDT(false));
    	addSequential(new SetGearFlap(true));
		addSequential(new SetFunnelCommand(false));
	    addSequential(new DrivetrainPIDCommand(75, false)); // 85.375, 78.375, 70, 68, 70.5, 68, 69
	    addSequential(new DrivetrainPIDCommand(-65.5, true));
	    addSequential(new DrivetrainPIDCommandWithTimeout(61.5,false));
	    addSequential(new SetGearFlap(false));
	    addSequential(new WaitCommand(0.5));
		addSequential(new DrivetrainPIDCommand(-20, false));

    }
}
