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

    	if(Robot.shoot.getLeftFlywheelEin() < minRPM){
    		Robot.shoot.setLeftFlywheel(1.0);
    	}else if(Robot.shoot.getLeftFlywheelEin() > maxRPM){
    		Robot.shoot.setLeftFlywheel(0.0);
    	}else if(Robot.shoot.getLeftFlywheelEin() <= targetRPM){
    		Robot.shoot.setLeftFlywheel(maxSpeed);
    	}else{
    		Robot.shoot.setLeftFlywheel(minSpeed);

    	}
    	if(Robot.shoot.getRightFlywheelEin() < minRPM){
    		Robot.shoot.setRightFlywheel(1.0);
    	}else if(Robot.shoot.getRightFlywheelEin() > maxRPM){
    		Robot.shoot.setRightFlywheel(0.0);
    	}else if(Robot.shoot.getRightFlywheelEin() <= targetRPM){
    		Robot.shoot.setRightFlywheel(maxSpeed);
    	}else{
    		Robot.shoot.setRightFlywheel(minSpeed);
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
