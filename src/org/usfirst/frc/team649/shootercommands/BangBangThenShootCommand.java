package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BangBangThenShootCommand extends Command {
	int targetRPM;
	double maxLeftSpeed;
	double minLeftSpeed;
	double minRightSpeed;
	double maxRightSpeed;
	boolean isAtSpeed;
	double minRPMThresh;
	double maxRPMThresh;
	Timer shootOneBall;
	double ballShootTime;
	
    public BangBangThenShootCommand(int targetRPM, double minSpeedRight, double maxSpeedRight, double maxSpeedLeft, double minSpeedLeft, int minRPMThresh, int maxRPMThresh) {
    	requires(Robot.shoot);
    	this.targetRPM = targetRPM;
    	maxLeftSpeed = maxSpeedLeft;
    	minLeftSpeed = minSpeedLeft;
    	maxRightSpeed = maxSpeedRight;
    	minRightSpeed = minSpeedRight;
    	this.minRPMThresh = minRPMThresh;
    	this.maxRPMThresh = maxRPMThresh;
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    } 

    // Called just before this Command runs the first time
    protected void initialize() {
    	shootOneBall = new Timer();
    	ballShootTime = 0.1;
    	isAtSpeed = false;
    	Robot.shoot.setLeftFlywheel(maxLeftSpeed);
    	Robot.shoot.setRightFlywheel(maxRightSpeed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.shoot.getLeftFlywheelEin() >= targetRPM){
    		Robot.shoot.setLeftFlywheel(minLeftSpeed);
    	}else{
    		Robot.shoot.setLeftFlywheel(maxLeftSpeed);
    	}
    	if(Robot.shoot.getRightFlyhweelEin() >= targetRPM){
    		Robot.shoot.setRightFlywheel(minRightSpeed);
    	}else{
    		Robot.shoot.setRightFlywheel(maxRightSpeed);
    	}
    	if(Robot.shoot.getLeftFlywheelEin() > minRPMThresh && Robot.shoot.getLeftFlywheelEin() < maxRPMThresh){
    		if(Robot.shoot.getRightFlyhweelEin() > minRPMThresh && Robot.shoot.getRightFlyhweelEin() < maxRPMThresh){
    			if(!isAtSpeed){
        			shootOneBall.start();
        			isAtSpeed = true;
    			}
    		}
    		
    	}
    	if(isAtSpeed){
    		Robot.shoot.feederMotor.set(Robot.shoot.feederSpeed);
    	}
    	if(shootOneBall.get()>ballShootTime && Robot.shoot.getLeftFlywheelEin() < minRPMThresh && Robot.shoot.getRightFlyhweelEin() < minRPMThresh){
    		isAtSpeed = false;
    		Robot.shoot.feederMotor.set(0.0);
    		shootOneBall.stop();
    		shootOneBall.reset();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.oi.operator.getShoot();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shoot.feederMotor.set(0.0);
    	Robot.shoot.leftFlywheel.set(0.0);
    	Robot.shoot.rightFlywheel.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
