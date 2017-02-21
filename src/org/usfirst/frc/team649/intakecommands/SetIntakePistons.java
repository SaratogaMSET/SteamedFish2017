package org.usfirst.frc.team649.intakecommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetIntakePistons extends Command {
	boolean setPistonsDown; 
    public SetIntakePistons(boolean down) {
    	setPistonsDown = down;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intake.setIntakeSol(setPistonsDown);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//        return Robot.intake.isIntakeDown();
    	return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(setPistonsDown){
    		Robot.prevStateIntakePistons = true;
    	}else{
    		Robot.prevStateIntakePistons = false;
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
