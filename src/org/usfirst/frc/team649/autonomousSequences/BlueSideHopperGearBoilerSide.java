package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.gearcommands.SetFunnelFlywheels;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.TurnWithEncoders;
import org.usfirst.frc.team649.shootercommands.OnlyBangBangNoShootCommand;
import org.usfirst.frc.team649.shootercommands.SetHoodCommand;
import org.usfirst.frc.team649.shootercommands.TurretPID;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class BlueSideHopperGearBoilerSide extends CommandGroup {

    public BlueSideHopperGearBoilerSide() {
    	addSequential(new DrivetrainPIDCommand(92, false));
    	addSequential(new DrivetrainPIDCommand(90, true));//addSequential(new TurnWithEncoders(90));
    	addParallel(new TurretPID(180));
    	addParallel(new SetFunnelCommand(true));
    	addParallel(new SetFunnelFlywheels(1));
    	addSequential(new DrivetrainPIDCommand(19.375, false));
//    	addParallel(new OnlyBangBangNoShootCommand(1600,0.3,0.4,0.4,0.3,1500,1700));
    	addParallel(new SetHoodCommand(40)); //temporary
//    	addSequential(new FeedBallsToShooterForTimeCommand(2.0));
    	addSequential(new DrivetrainPIDCommand(-37.5, false));
    	addParallel(new SetFunnelCommand(false));
    	addParallel(new TurretPID(72));
    	addSequential(new DrivetrainPIDCommand(-150, true));//addSequential(new TurnWithEncoders(-150));
    	addSequential(new DrivetrainPIDCommand(63.875, false));
//    	addParallel(new OnlyBangBangNoShootCommand(1600,0.3,0.4,0.4,0.3,1500,1700));
    	addSequential(new SetGearFlap(true));
//    	addParallel(new FeedBallsToShooterForTimeCommand(15.0));
    	addSequential(new WaitCommand(0.5));
    	addSequential(new SetGearFlap(false));
    }
}
