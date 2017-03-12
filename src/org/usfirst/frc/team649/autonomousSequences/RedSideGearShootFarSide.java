package org.usfirst.frc.team649.autonomousSequences;

import javax.sound.midi.Sequence;

import org.usfirst.frc.team649.gearcommands.SetGearFlap;
import org.usfirst.frc.team649.robot.commands.DrivetrainPIDCommand;
import org.usfirst.frc.team649.robot.commands.TurnWithEncoders;
import org.usfirst.frc.team649.shootercommands.OnlyBangBangNoShootCommand;
import org.usfirst.frc.team649.shootercommands.ShooterPID;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RedSideGearShootFarSide extends CommandGroup {

    public RedSideGearShootFarSide() {
    	addSequential(new DrivetrainPIDCommand(70.565, false));
    	addSequential(new DrivetrainPIDCommand(-60, true));//addSequential(new TurnWithEncoders(-60));
    	addSequential(new DrivetrainPIDCommand(56.535, false));
    	addSequential(new WaitCommand(0.25));
     	addSequential(new SetGearFlap(false));
     	addSequential(new DrivetrainPIDCommand(-56.535, false));
//     	addParallel(new OnlyBangBangNoShootCommand(1600,0.3,0.4,0.4,0.3,1500,1700));
    	addSequential(new DrivetrainPIDCommand(90, true));//addSequential(new TurnWithEncoders(90));
//    	addSequential(new FeedBallsToShooterForTimeCommandWithDelays(15.0,0.25,0.5));
    }
}