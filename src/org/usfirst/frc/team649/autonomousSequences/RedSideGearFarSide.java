package org.usfirst.frc.team649.autonomousSequences;

import javax.sound.midi.Sequence;

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
public class RedSideGearFarSide extends CommandGroup {

    public RedSideGearFarSide() {
    	addSequential(new SwitchDTMode(true));
    	addSequential(new ShiftDT(false));
    	addSequential(new SetGearFlap(true));
//		addSequential(new SetIntakeWedgePistons(false));
		addSequential(new SetFunnelCommand(false));
	    addSequential(new DrivetrainPIDCommand(73, false)); // 85.375, 78.375, 70, 68, 70.5, 68, 69
	    addSequential(new DrivetrainPIDCommand(60, true));
	    addSequential(new DrivetrainPIDCommand(61.5,false));  
	    addSequential(new SetGearFlap(false));
	    addSequential(new WaitCommand(0.5));
		addSequential(new DrivetrainPIDCommand(-20, false));
    }
}