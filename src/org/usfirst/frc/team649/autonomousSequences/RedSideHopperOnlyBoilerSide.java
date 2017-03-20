package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.gearcommands.SetFunnelFlywheels;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.ShiftDT;
import org.usfirst.frc.team649.robot.commands.SwitchDTMode;
import org.usfirst.frc.team649.robot.commands.TurnWithEncoders;
import org.usfirst.frc.team649.shootercommands.OnlyBangBangNoShootCommand;
import org.usfirst.frc.team649.shootercommands.SetHoodCommand;
import org.usfirst.frc.team649.shootercommands.TurretPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RedSideHopperOnlyBoilerSide extends CommandGroup {

    public RedSideHopperOnlyBoilerSide() {
    	addSequential(new SwitchDTMode(true));
    	addSequential(new ShiftDT(false));
    	addSequential(new DrivetrainPIDCommand(92, false));
    	addSequential(new DrivetrainPIDCommand(-90, true));//addSequential(new TurnWithEncoders(-90));
    	addParallel(new SetFunnelCommand(true));
    	addParallel(new SetFunnelFlywheels(1));
    	addSequential(new DrivetrainPIDCommand(19.375, false));
//    	addParallel(new OnlyBangBangNoShootCommand(1600,0.3,0.4,0.4,0.3,1500,1700));
    	addParallel(new SetHoodCommand(40)); //temporary
//    	addSequential(new FeedBallsToShooterForTimeCommand(15.0));
    }
}
