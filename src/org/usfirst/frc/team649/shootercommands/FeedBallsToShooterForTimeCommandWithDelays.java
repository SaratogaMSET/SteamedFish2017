package org.usfirst.frc.team649.shootercommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FeedBallsToShooterForTimeCommandWithDelays extends Command {
	double timeShooting;
	double delayPer;
	double timeFeeding;
	Timer time;
    public FeedBallsToShooterForTimeCommandWithDelays(double timeShooting, double delayPer, double timeFeeding) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.timeShooting = timeShooting;
    	time.start();

    }

    // Called just before this Command runs the first time
    protected void initialize() {
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
}
