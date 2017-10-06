package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DrivetrainPIDCommandTurnTimeout extends Command {
	double distance;
	public PIDController drivePID;
	public double encoderRight;
	public double encoderLeft;
	double startAngle;
	double endAngle;
	public Timer time;
	public Timer timeOut;
	boolean isFinished;
    public DrivetrainPIDCommandTurnTimeout(double distance) {
    	requires(Robot.drive);    	
    	drivePID = Robot.drive.getPIDController();
    	this.distance = distance;
    	time = new Timer();
    	timeOut = new Timer();
    
    	SmartDashboard.putBoolean("Is it finished", isFinished);
    	startAngle = 0;
    	endAngle = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    
    	Robot.drive.resetEncoders();
    	drivePID.enable();
    	//drivePIDRight.enable();
    	SmartDashboard.putBoolean("is Auto asdkfj", Robot.isAutoTimedOut);
    	if(Robot.isAutoTimedOut){
    		isFinished = false;
    	}else{
    		isFinished = true;
    	}
    	Robot.isPIDActive = true;
    	double setpoint;
    	setpoint = Robot.drive.getPosition() + Robot.drive.getTranslationalDistanceForTurn(distance);
		drivePID.setPID(0.2, 0, 0.01);
		drivePID.setSetpoint(setpoint);
		timeOut.start();
		time.start();
		Robot.isPIDTurn = true;
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
		if(drivePID.onTarget() && time.get() < 0.01){
    		time.start();
    	}else if(time.get() > 0.01 && !drivePID.onTarget()){
    		time.stop();
    		time.reset();
    	}
    	if(time.get()> 0.2){
    		isFinished = true;
    	}
    	if(timeOut.get() > 1.25){
    		Robot.isAutoTimeoutTurnTimedOut = true;

    		isFinished = true;
    	}

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	encoderRight = Robot.drive.rightEncoder.getDistance();
    	encoderLeft = Robot.drive.leftEncoder.getDistance();
//    	endAngle = Robot.gyro.getAngle();
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivePID.disable();
    	Robot.drive.rawDrive(0, 0);
    	Robot.isPIDActive = false;
    	time.stop();
    	time.reset();
    	timeOut.stop();
    	time.reset();
    	Robot.isPIDTurn = false;

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
