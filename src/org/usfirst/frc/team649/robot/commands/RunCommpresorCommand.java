package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunCommpresorCommand extends Command {
	boolean on;
    public RunCommpresorCommand(boolean state) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	on = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.compressor.setClosedLoopControl(on);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    public boolean getCompressorState()
    {
    	return on;
    }
}
