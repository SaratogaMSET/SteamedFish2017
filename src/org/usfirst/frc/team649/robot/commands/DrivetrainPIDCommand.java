package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class  DrivetrainPIDCommand extends Command {
	double distance;
	public PIDController drivePID;
	public double encoderRight;
	public double encoderLeft;
	double startAngle;
	double endAngle;
	public Timer time;
	public Timer timeout;
	boolean isFinished;
	boolean isTurning;
	//double distanceTraveled = 2000;
	
    public DrivetrainPIDCommand(double distance, boolean isTurning) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	
    	drivePID = Robot.drive.getPIDController();
    	//drivePIDRight   = Robot.drivetrain.getPIDController();
    	this.distance = distance;
    	this.isTurning = isTurning;
    	time = new Timer();
    	isFinished = false;
    	startAngle = 0;
    	endAngle = 0;
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
//    	Robot.gyro.resetGyro();
//		startAngle = Robot.gyro.getAngle();
    	Robot.isPIDTurn = isTurning;
    	Robot.drive.resetEncoders();
    	drivePID.enable();
    	//drivePIDRight.enable();
    	Robot.isPIDActive = true;
    	double setpoint;
    	if (Robot.isPIDTurn == true) { //positive angle is turning to the right
    		setpoint = Robot.drive.getPosition() + Robot.drive.getTranslationalDistanceForTurn(distance);
    		drivePID.setPID(0.2, 0, 0.01); //turning needs new PID values these are pretty good
    		//setpoint = Robot.drivetrain.getGyroValue() + distance;
    	} else {
    		setpoint = Robot.drive.getPosition() + distance;
    		drivePID.setPID(0.03, 0, 0.05);
    	}
    	drivePID.setSetpoint(setpoint);
    	SmartDashboard.putNumber("Setpoint", setpoint);
    	//drivePIDRight.setSetpoint(setpoint);
    	System.out.println("DT PID: setpoint = " + setpoint);
    	
//    	Robot.logMessage("DT driving with PID, initial Enc: " + Robot.drivetrain.getPosition() + ", moving to: " + setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
//    	if(Math.abs(Robot.drive.getDistanceDTBoth() - distanceTraveled) < 0.01){
//    		isFinished = true;
//    	}
    	if(drivePID.onTarget() && time.get() < 0.01){
    		time.start();
    	}else if(time.get() > 0.01 && !drivePID.onTarget()){
    		time.stop();
    		time.reset();
    	}
    	if(time.get()> 0.2){
    		isFinished = true;
    	}
    	//distanceTraveled = Robot.drive.getDistanceDTBoth();
    	SmartDashboard.putBoolean("DrivePID on Target?", drivePID.onTarget());
    	SmartDashboard.putBoolean("is done", drivePID.onTarget());
    	SmartDashboard.putString("DT Current Command", this.getName());
    	SmartDashboard.putBoolean("Turning", isTurning);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {

    	encoderRight = Robot.drive.rightEncoder.getDistance();
    	encoderLeft = Robot.drive.leftEncoder.getDistance();
//    	endAngle = Robot.gyro.getAngle();
        return isFinished;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	drivePID.disable();
    	SmartDashboard.putBoolean("End", true);
    	SmartDashboard.putNumber("encoder Right", encoderRight);
    	SmartDashboard.putNumber("encoder left", encoderLeft);
    	SmartDashboard.putBoolean("pid done", true);
    	Robot.drive.rawDrive(0, 0);
    	//drivePIDRight.disable();
    	Robot.isPIDActive = false;
    	time.stop();
    	time.reset();
//    	double deltaAngle = endAngle - startAngle;
//    	if (isTurning) {
//    		Robot.turnAngle = deltaAngle;
//    	} else {
//    		if (Robot.straightAngle1 == 0) {
//    			Robot.straightAngle1 = deltaAngle;
//    		} else {
//    			Robot.straightAngle2 = deltaAngle;
//    		}
//    	}
    	//Robot.drivetrain.resetEncoders();
    	//Robot.drive.printPIDValues();

//    	Robot.logMessage("DT ended at: " + Robot.drivetrain.getDistanceDTBoth());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	end();
    }
}