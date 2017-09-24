package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;
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
public class BlueSideNoGearShootMiddle extends CommandGroup {

    public BlueSideNoGearShootMiddle() {
    		addSequential(new SwitchDTMode(true));
    		addSequential(new ShiftDT(false));
    	    addSequential(new SetGearFlap(true));
    		addSequential(new SetFunnelCommand(false));
    	    addSequential(new DrivetrainPIDCommand(44, false)); 
//    	    addSequential(new SetGearFlap(false));
    	    addParallel(new TurretPIDABS(0.21*60));
    	    addParallel(new SetHoodCommand(0.453)); 
    	    addParallel(new OnlyBangBangNoShootCommand(2050,2150,1950,GetShooterValues.returnShooterMaxPower(2050),GetShooterValues.returnShooterMinPower(2050))); //1725, turret .98-.55 hood 0.625
    		addSequential(new FeedBallsToShooterCommand(1.0));
    }

}
