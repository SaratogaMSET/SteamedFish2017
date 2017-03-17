package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurretPID extends Command {
	double setPoint;
	public PIDController turretPID;
	Timer time;
	boolean isFinished;
    public TurretPID(double angle) {
    	requires(Robot.turret);
    	Robot.turret.countCurrentPosition();
    	turretPID = Robot.turret.getPIDController();
    	Robot.turret.countCurrentPosition();
    	setPoint = Robot.turret.translateAngleToABS(angle) + Robot.turret.getTotalDist();
    	time = new Timer();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	turretPID.enable();
    	Robot.isTurretPIDActive = true;
    	turretPID.setSetpoint(setPoint);
    	isFinished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.turret.countCurrentPosition();
    	if(turretPID.onTarget() && time.get() < 0.01){
    		time.start();
    	}
    	if(time.get() > 0.1){
    		if(turretPID.onTarget()){
    			isFinished = true;
    		}else{
    			time.stop();
    			time.reset();
    		}
    	}
    	SmartDashboard.putBoolean("is On target", turretPID.onTarget());
    	SmartDashboard.putNumber("setpoint turret PID", setPoint);
    	SmartDashboard.putNumber("distance",Robot.turret.getTotalDist());
    	SmartDashboard.putBoolean("is Turret Max", Robot.isTurretMax);
    	SmartDashboard.putBoolean("is Turret Min", Robot.isTurretMin);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	return isFinished || Robot.isTurretMax || Robot.isTurretMin;
    	return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	SmartDashboard.putBoolean("is turret Finished", false);
    	turretPID.disable();
    	SmartDashboard.putBoolean("isTurretPID enabled", turretPID.isEnabled());

    	Robot.turret.manualSet(0.0);
    	time.stop();
    	time.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
