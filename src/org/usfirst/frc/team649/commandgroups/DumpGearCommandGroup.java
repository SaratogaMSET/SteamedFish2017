package org.usfirst.frc.team649.commandgroups;

import org.usfirst.frc.team649.gearcommands.SetIntakeGearCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class DumpGearCommandGroup extends CommandGroup {

    public DumpGearCommandGroup() {
    	addSequential(new SetIntakeGearCommands(true));
    	addSequential(new WaitCommand(0.25));
    	addSequential(new SetIntakeGearCommands(false));
      
    }
}
