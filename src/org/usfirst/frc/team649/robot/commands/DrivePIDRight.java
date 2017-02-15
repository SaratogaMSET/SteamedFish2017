package org.usfirst.frc.team649.robot.commands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DrivePIDRight extends Command {
	double distance;
	double tolerance = 0.25;
	public PIDController drivePID;
	
    public DrivePIDRight(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivePID = Robot.rightDT.getPIDController();
    	
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    	drivePID.enable();
    	double setpoint = Robot.rightDT.getPosition() + distance;
    	drivePID.setSetpoint(setpoint);
    	SmartDashboard.putNumber("setpoint right", setpoint);
//    	System.out.println("INIT RIGHT PID");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
//    	System.out.println("RUNNING RIGHT PID");
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {

        return drivePID.onTarget();
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	drivePID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    }
}


