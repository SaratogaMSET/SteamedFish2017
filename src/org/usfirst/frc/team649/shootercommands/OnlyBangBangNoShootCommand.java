package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OnlyBangBangNoShootCommand extends Command {
	int targetRPM;
	double maxLeftSpeed;
	double minLeftSpeed;
	double minRightSpeed;
	double maxRightSpeed;
	boolean isAtSpeed;
	double minRPMThresh;
	double maxRPMThresh;
    public OnlyBangBangNoShootCommand(int targetRPM, double minSpeedRight, double maxSpeedRight, double maxSpeedLeft, double minSpeedLeft, int minRPMThresh, int maxRPMThresh) {
    	this.targetRPM = targetRPM;
    	maxLeftSpeed = maxSpeedLeft;
    	minLeftSpeed = minSpeedLeft;
    	maxRightSpeed = maxSpeedRight;
    	minRightSpeed = minSpeedRight;
    	this.minRPMThresh = minRPMThresh;
    	this.maxRPMThresh = maxRPMThresh;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.isShooterRunning = true;
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
    	if(Robot.shoot.getRightFlyhweelEin() < minRPMThresh){
    		Robot.shoot.setRightFlywheel(1.0);
    	}else if(Robot.shoot.getRightFlyhweelEin() > maxRPMThresh){
    		Robot.shoot.setRightFlywheel(0.0);
    	}else if(Robot.shoot.getRightFlyhweelEin() <= targetRPM){
    		Robot.shoot.setRightFlywheel(maxLeftSpeed);
    	}else{
    		Robot.shoot.setRightFlywheel(minLeftSpeed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.isShooterRunning;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shoot.setLeftFlywheel(0.0);
    	Robot.shoot.setRightFlywheel(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
