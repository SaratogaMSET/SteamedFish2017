package org.usfirst.frc.team649.commandgroups;

import org.usfirst.frc.team649.shootercommands.ResetTurretValue;
import org.usfirst.frc.team649.shootercommands.TurretPIDABS;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ResetTurretSequence extends CommandGroup {

    public ResetTurretSequence() {
       addSequential(new ResetTurretValue());
       addSequential(new TurretPIDABS(0));
    }
}
