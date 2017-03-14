package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FastBangBangThenShoot extends Command {
	int targetRPM;
	double maxLeftSpeed;
	double minLeftSpeed;
	double minRightSpeed;
	double maxRightSpeed;
	boolean isAtSpeed;
	double minRPMThresh;
	double maxRPMThresh;
	Timer hooperTimer;
    public FastBangBangThenShoot(int targetRPM, double minSpeedRight, double maxSpeedRight, double maxSpeedLeft, double minSpeedLeft, int minRPMThresh, int maxRPMThresh) {
    	requires(Robot.shoot);
    	this.targetRPM = targetRPM;
    	maxLeftSpeed = maxSpeedLeft;
    	minLeftSpeed = minSpeedLeft;
    	maxRightSpeed = maxSpeedRight;
    	minRightSpeed = minSpeedRight;
    	this.minRPMThresh = minRPMThresh;
    	this.maxRPMThresh = maxRPMThresh;
    	hooperTimer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isAtSpeed = false;
    	hooperTimer.start();
    	Robot.shoot.feederMotor.set(0.0);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.shootLeft.getLeftFlywheelEin() < minRPMThresh){
    		Robot.shootLeft.setLeftFlywheel(1.0);
    	}else if(Robot.shootLeft.getLeftFlywheelEin() > maxRPMThresh){
    		Robot.shootLeft.setLeftFlywheel(0.0);
    	}else if(Robot.shootLeft.getLeftFlywheelEin() <= targetRPM){
    		Robot.shootLeft.setLeftFlywheel(maxLeftSpeed);
    	}else{
    		Robot.shootLeft.setLeftFlywheel(minLeftSpeed);
    	}
    	if(Robot.shootRight.getRightFlywheelEin() < minRPMThresh){
    		Robot.shootRight.setRightFlywheel(1.0);
    	}else if(Robot.shootRight.getRightFlywheelEin() > maxRPMThresh){
    		Robot.shootRight.setRightFlywheel(0.0);
    	}else if(Robot.shootRight.getRightFlywheelEin() <= targetRPM){
    		Robot.shootRight.setRightFlywheel(maxLeftSpeed);
    	}else{
    		Robot.shootRight.setRightFlywheel(minLeftSpeed);
    	}
    	if(Robot.shootLeft.getLeftFlywheelEin() > minRPMThresh){
    		isAtSpeed = true;
    	}
    	if(isAtSpeed){
    		Robot.shoot.setFeedMotor(1.0);
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.oi.operator.getShoot();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shoot.feederMotor.set(0.0);
    
    	Robot.shootLeft.setLeftFlywheel(0.0);
    	Robot.shootRight.setRightFlywheel(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
