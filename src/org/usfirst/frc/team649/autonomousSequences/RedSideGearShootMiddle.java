package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.shootercommands.BackToZeroTurretCommand;
import org.usfirst.frc.team649.shootercommands.FeedBallsToShooterForTimeCommand;
import org.usfirst.frc.team649.shootercommands.OnlyBangBangNoShootCommand;
import org.usfirst.frc.team649.shootercommands.SetHoodCommand;
import org.usfirst.frc.team649.shootercommands.ShooterPID;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RedSideGearShootMiddle extends CommandGroup {

    public RedSideGearShootMiddle() {
        addSequential(new DrivetrainPIDCommand(85.375, false));
        addParallel(new BackToZeroTurretCommand());
        addSequential(new SetGearFlap(true));
        addParallel(new ShooterPID(42));
    	addParallel(new SetHoodCommand(40)); //temporary
    	addParallel(new OnlyBangBangNoShootCommand(1600,0.3,0.4,0.4,0.3,1500,1700));
        addSequential(new WaitCommand(0.25));
    	addSequential(new SetGearFlap(false));  
    	addSequential(new DrivetrainPIDCommand(-3, false));
    	addSequential(new FeedBallsToShooterForTimeCommand(15.0));

    }
}
