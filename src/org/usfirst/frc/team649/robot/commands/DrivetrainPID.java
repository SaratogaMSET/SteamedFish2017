package org.usfirst.frc.team649.robot.commands.drivetrain;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DrivetrainPID extends Command {
	
	double distance;
	double tolerance = 1.0;
	//public PIDController drivePID;
	double setpoint;
	DrivePIDLeft left;
	DrivePIDRight right;
	
    public DrivetrainPID(double distance) {
    	requires(Robot.drive);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//drivePID = Robot.drive.getPIDController();
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("IS AUTO", "Yes");
    	right = new DrivePIDRight(distance);
    	left = new DrivePIDLeft(distance);
//    	drivePID.enable();
//    	SmartDashboard.putBoolean("Is enabled", true);
//    	double setpoint = distance + Robot.drive.getPosition();
//    	drivePID.setSetpoint(setpoint);
    	//drivePIDRight.enable();
//    	Robot.isPIDActive = true;
//    	double setpoint = Robot.drivetrain.getPosition() + distance;
//    	drivePID.setSetpoint(setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putBoolean("Is executing", true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean done = !right.isRunning() && !left.isRunning();
        SmartDashboard.putBoolean("Done?", done);
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//drivePID.disable();
    	Robot.drive.rawDrive(0, 0);
    	Robot.drive.resetEncoders();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}



