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
import org.usfirst.frc.team649.shootercommands.TurretPID;
import org.usfirst.frc.team649.shootercommands.TurretPIDABS;
import org.usfirst.frc.team649.util.GetShooterValues;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class BlueSideGearShootMiddle extends CommandGroup {

    public BlueSideGearShootMiddle() {
    			addSequential(new SwitchDTMode(true));
    	addSequential(new ShiftDT(false));
    	    addSequential(new SetGearFlap(true));
    		addSequential(new SetIntakeWedgePistons(false));
    		addSequential(new SetFunnelCommand(false));
    	    addSequential(new DrivetrainPIDCommand(72, false)); // 85.375, 78.375, 70, 68, 70.5, 68, 69
    	    addSequential(new SetGearFlap(false));
    	    addSequential(new WaitCommand(1));
    	    addParallel(new TurretPIDABS(-0.43*60));
    		addParallel(new SetHoodCommand(0.625)); //temporary
    	    addParallel(new OnlyBangBangNoShootCommand(1725,1925,1525,GetShooterValues.returnShooterMaxPower(1725),GetShooterValues.returnShooterMinPower(1725))); //1725, turret .98-.55 hood 0.625
    		addSequential(new DrivetrainPIDCommand(-30, false));
    		addSequential(new FeedBallsToShooterCommand(1.0));
    }
 
}
