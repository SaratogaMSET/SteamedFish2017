package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ResetTurretValue extends Command {
	double firstHal;
	double secondHal;
	int halCount;
	boolean prevStateHal;
    public ResetTurretValue() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.turret.turn(0.15);
    	prevStateHal = false;
    	halCount = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("number hal trip" , halCount);
    	if(Robot.turret.getTurretHal() && !prevStateHal){
    		prevStateHal = true;
    		if(halCount == 0){
    			firstHal = Robot.turret.getTurretEncoderValue();
    			Robot.turret.turn(-0.15);
    		}else if(halCount == 1){
    			secondHal = Robot.turret.getTurretEncoderValue();
    			Robot.turret.turn(0);
    		}
    		halCount++;
    	}
    	if(Robot.turret.getTurretHal()){
    		prevStateHal = true;
    	}else{
    		prevStateHal = false;
    	}
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return halCount > 1;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.turret.turn(0);
    	Robot.turret.resetEncoder();
    	Robot.turret.startingPos = Math.abs(secondHal-firstHal);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
