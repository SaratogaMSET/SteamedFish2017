package org.usfirst.frc.team649.hangcommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HookCommand extends Command {
	boolean isHook;
    public HookCommand(boolean in) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	isHook = in;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.hang.setHookSol(isHook);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.hang.getHookSol() == isHook){
    		return true;
    	}else{
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
