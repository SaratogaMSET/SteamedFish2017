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
    	Robot.shoot.setHooperIn(0.0);
    	Robot.shoot.setHooperOutRaw(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.shoot.getLeftFlywheelEin() < minRPMThresh){
    		Robot.shoot.setLeftFlywheel(1.0);
    	}else if(Robot.shoot.getLeftFlywheelEin() > maxRPMThresh){
    		Robot.shoot.setLeftFlywheel(0.0);
    	}else if(Robot.shoot.getLeftFlywheelEin() <= targetRPM){
    		Robot.shoot.setLeftFlywheel(maxLeftSpeed);
    	}else{
    		Robot.shoot.setLeftFlywheel(minLeftSpeed);
    	}
    	if(Robot.shoot.getRightFlywheelEin() < minRPMThresh){
    		Robot.shoot.setRightFlywheel(1.0);
    	}else if(Robot.shoot.getRightFlywheelEin() > maxRPMThresh){
    		Robot.shoot.setRightFlywheel(0.0);
    	}else if(Robot.shoot.getRightFlywheelEin() <= targetRPM){
    		Robot.shoot.setRightFlywheel(maxLeftSpeed);
    	}else{
    		Robot.shoot.setRightFlywheel(minLeftSpeed);
    	}
    	if(Robot.shoot.getLeftFlywheelEin() > minRPMThresh){
    		isAtSpeed = true;
    	}
    	if(isAtSpeed){
    		Robot.shoot.setFeedMotor(Robot.shoot.FEEDER_SPEED);
    		Robot.shoot.setHooperIn(1.0);
    		Robot.shoot.setHooperOut(0.2, 0.8, 1, hooperTimer.get());
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.oi.operator.getShoot();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shoot.feederMotor.set(0.0);
    	Robot.shoot.setHooperIn(0.0);
    	Robot.shoot.setHooperOutRaw(0.0);
    	Robot.shoot.setLeftFlywheel(0.0);
    	Robot.shoot.setRightFlywheel(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
