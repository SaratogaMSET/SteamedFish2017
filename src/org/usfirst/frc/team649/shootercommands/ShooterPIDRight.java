package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterPIDRight extends Command {
	double setPoint;
	public PIDController shooterRightPID;
	Timer time;
	boolean isFinished;
    public ShooterPIDRight(double RPM) {
    	requires(Robot.shootRight);
    	shooterRightPID = Robot.shootRight.getPIDController();
    	setPoint = RPM;
    	time = new Timer();

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shooterRightPID.enable();
    	shooterRightPID.setSetpoint(setPoint);
    	isFinished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.oi.operator.getShoot()&& time.get() < 0.01){
    		time.start();
    	}
    	if(time.get() > 0.3){
    		if(!Robot.oi.operator.getShoot()){
    			isFinished = true;
    		}else{
    			time.stop();
    			time.reset();
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooterRightPID.disable();
    	Robot.shootRight.setRightFlywheel(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
