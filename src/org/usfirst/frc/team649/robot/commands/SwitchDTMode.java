package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchDTMode extends Command {
	boolean isBrake;
    public SwitchDTMode(boolean isBrake) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.isBrake = isBrake;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	for(int i = 0;i<4;i++){
    		Robot.drive.motors[i].enableBrakeMode(isBrake);
    	}
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
