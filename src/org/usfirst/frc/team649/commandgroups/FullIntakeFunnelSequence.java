package org.usfirst.frc.team649.commandgroups;

import org.usfirst.frc.team649.gearcommands.RunFunnelWheelsForBalls;
import org.usfirst.frc.team649.gearcommands.RunFunnelWheelsForGear;
import org.usfirst.frc.team649.gearcommands.SetFunnelCommand;
import org.usfirst.frc.team649.gearcommands.SetGearFlap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FullIntakeFunnelSequence extends CommandGroup {

    public FullIntakeFunnelSequence() {
    	addSequential(new SetGearFlap(false));
       addSequential(new SetFunnelCommand(true));
       addSequential(new RunFunnelWheelsForGear());
       addSequential(new RunFunnelWheelsForBalls());
    }
}
