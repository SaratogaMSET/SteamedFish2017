package org.usfirst.frc.team649.commandgroups;

import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class DumpGearCommandGroup extends CommandGroup {

    public DumpGearCommandGroup() {
    	addSequential(new SetGearFlap(true));
    	addSequential(new WaitCommand(0.5));
    	addSequential(new SetGearFlap(false));
      
    }
}
