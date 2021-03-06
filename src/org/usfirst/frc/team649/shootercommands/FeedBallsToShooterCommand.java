package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FeedBallsToShooterCommand extends Command {
	double speed;
	Timer time;
    public FeedBallsToShooterCommand(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.speed = speed;
    	time = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shoot.setFeedMotor(speed);
    	Robot.intake.redWheelRoller.set(-0.6);
    	Robot.shoot.setHooperMotor(speed);
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
    	Robot.shoot.setFeedMotor(0);
    	Robot.intake.redWheelRoller.set(0);
    	Robot.shoot.setHooperMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
