package org.usfirst.frc.team649.gearcommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetFunnelCommand extends Command {
	
	boolean isOut;
	/**
	 * Used to set the intake stuffs
	 * @param state
	 */
    public SetFunnelCommand(boolean state) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	isOut = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gear.setFunnelPistonState(isOut);
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
    	if(isOut){
    		Robot.prevStateFunnelFlap = true;
    	}else{
    		Robot.prevStateFunnelFlap = false;
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
