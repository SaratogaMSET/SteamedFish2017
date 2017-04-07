package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ConditionalWaitCommand extends Command {
	Timer time;
	boolean isFinished;
	double timeRun;
    public ConditionalWaitCommand(double timeSec) {
        time = new Timer();
        isFinished = false;
        timeRun = timeSec;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!Robot.autoTimeout){
    		isFinished = true;
    	}
    	time.start();
  
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(time.get()>timeRun){
    		isFinished = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	time.stop();
    	time.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
