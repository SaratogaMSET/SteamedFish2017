package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

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
	Timer timePerCycle;
    public FeedBallsToShooterForTimeCommandWithDelays(double timeShooting, double delayPer, double timeFeeding) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.timeShooting = timeShooting;
    	time.start();
    	timePerCycle.start();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timePerCycle.get()<timeFeeding){
    		Robot.shoot.setFeedMotor(Robot.shoot.FEEDER_SPEED);
    		Robot.shoot.setHooperIn(1.0);
    		Robot.shoot.setHooperOut(0.2, 0.8, 1, time.get());
    	}else{
    		Robot.shoot.setFeedMotor(0.0);
    		Robot.shoot.setHooperIn(0.0);
    		Robot.shoot.setHooperOutRaw(0.0);
    	}
    	if(timePerCycle.get() > delayPer+timeFeeding){
    		timePerCycle.reset();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return time.get() >= timeShooting;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shoot.setFeedMotor(0.0);
		Robot.shoot.setHooperIn(0.0);
		Robot.shoot.setHooperOutRaw(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
