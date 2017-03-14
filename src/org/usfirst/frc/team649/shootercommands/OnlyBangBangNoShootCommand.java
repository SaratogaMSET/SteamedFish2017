package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OnlyBangBangNoShootCommand extends Command {
	int targetRPM;
	int maxRPM;
	int minRPM;
	double maxSpeed;
	double minSpeed;
    public OnlyBangBangNoShootCommand(int targetRPM, int maxRPM, int minRPM, double maxSpeed, double minSpeed) {
    	this.targetRPM = targetRPM;
    	this.maxRPM = maxRPM;
    	this.minRPM = minRPM;
    	this.maxSpeed = maxSpeed;
    	this.minSpeed = minSpeed;
    
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.isShooterRunning = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	if(Robot.shootLeft.getLeftFlywheelEin() < minRPM){
    		Robot.shootLeft.setLeftFlywheel(1.0);
    	}else if(Robot.shootLeft.getLeftFlywheelEin() > maxRPM){
    		Robot.shootLeft.setLeftFlywheel(0.0);
    	}else if(Robot.shootLeft.getLeftFlywheelEin() <= targetRPM){
    		Robot.shootLeft.setLeftFlywheel(maxSpeed);
    	}else{
    		Robot.shootLeft.setLeftFlywheel(minSpeed);
    	}
    	if(Robot.shootRight.getRightFlywheelEin() < minRPM){
    		Robot.shootRight.setRightFlywheel(1.0);
    	}else if(Robot.shootRight.getRightFlywheelEin() > maxRPM){
    		Robot.shootRight.setRightFlywheel(0.0);
    	}else if(Robot.shootRight.getRightFlywheelEin() <= targetRPM){
    		Robot.shootRight.setRightFlywheel(maxSpeed);
    	}else{
    		Robot.shootRight.setRightFlywheel(minSpeed);
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.isShooterRunning;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shootLeft.setLeftFlywheel(0.0);
    	Robot.shootRight.setRightFlywheel(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
