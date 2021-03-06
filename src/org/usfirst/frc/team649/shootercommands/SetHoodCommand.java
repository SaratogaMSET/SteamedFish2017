package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetHoodCommand extends Command {
	double raw;
    public SetHoodCommand(double raw) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.raw = raw;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.hood.setServoRaw(raw);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
