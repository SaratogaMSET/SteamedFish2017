package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearRetryCommand extends Command {
	
	double distance;
	DrivetrainPIDCommand backwards;
	DrivetrainPIDCommand forwards;
	boolean isFinished;

    public GearRetryCommand(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isFinished = false;
    	backwards = new DrivetrainPIDCommand(-distance, false);
    	forwards = new DrivetrainPIDCommand(distance, false);
    	backwards.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(backwards.isFinished()) {
    		backwards.end();
    		forwards.start();
    	}
    	if(forwards.isFinished()) {
    		isFinished = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
