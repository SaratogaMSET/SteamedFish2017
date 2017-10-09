package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GyroTurnPID extends Command {
	
	public PIDController drivePID;
	public double encoderRight;
	public double encoderLeft;
	public double gyroVal;
	public Timer time;
	public Timer timeout;
	boolean isTimeout;
	boolean isFinished;
	double angle;
	String actuallyFinished;
	
    public GyroTurnPID(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.angle = angle;
    	drivePID = Robot.gyro.getPIDController();
    	time = new Timer();
    	timeout = new Timer();
    	isFinished = false;
    	isTimeout = false;
    	actuallyFinished = "false";
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivePID.enable();
    	double setpoint = Robot.gyro.getAngle() + angle;
    	
    	Robot.gyro.resetGyro();
    	    	
    	drivePID.setSetpoint(setpoint);
    	SmartDashboard.putNumber("Setpoint", setpoint);
    	time.start();
    	timeout.start();
    	//drivePIDRight.setSetpoint(setpoint);
    	System.out.println("DT PID: setpoint = " + setpoint);
    	
    	Robot.tick = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(drivePID.onTarget() && time.get() < 0.01){
    		time.start();
    	}else if(time.get() > 0.01 && !drivePID.onTarget()){
    		time.stop();
    		time.reset();
    	}
    	if(time.get() > 0.2){
    		isFinished = true;
    		gyroVal = Robot.gyro.getAngle();
    		actuallyFinished = "true";
    	}
    	if (timeout.get() > 2) {
    		isFinished = true;
    		isTimeout = true;
    	}
    	
    	//distanceTraveled = Robot.drive.getDistanceDTBoth();
    	SmartDashboard.putBoolean("DrivePID on Target?", drivePID.onTarget());
    	SmartDashboard.putBoolean("is done", drivePID.onTarget());
    	SmartDashboard.putString("DT Current Command", this.getName());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	gyroVal = Robot.gyro.getAngle();
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivePID.disable();
    	SmartDashboard.putNumber("Gyro Final Reading", gyroVal);
    	SmartDashboard.putBoolean("Timeout", isTimeout);
    	SmartDashboard.putBoolean("End", true);
    	SmartDashboard.putBoolean("pid done", true);
    	Robot.drive.rawDrive(0, 0);
    	Robot.gyro.resetGyro();
    	//drivePIDRight.disable();
    	time.stop();
    	time.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    }
    
}