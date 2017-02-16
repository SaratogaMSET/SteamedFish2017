package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BackToZeroTurretCommand extends Command {

    public BackToZeroTurretCommand() {
    	requires(Robot.turret);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.turret.manualSet(-0.25);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.turret.getLeftHal();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.turret.resetEncoder();
    	Robot.turret.manualSet(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
