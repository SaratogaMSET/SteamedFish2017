package org.usfirst.frc.team649.robot.commands.drivetrain;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DrivePIDLeft extends Command {
	double distance;
	double tolerance = 0.25;
	public PIDController drivePID;
	
    public DrivePIDLeft(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivePID = Robot.leftDT.getPIDController();
    	
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    	double setpoint = Robot.leftDT.getPosition() + distance;
    	drivePID.setSetpoint(setpoint);
    	SmartDashboard.putNumber("setpoint left", setpoint);
    	drivePID.enable();
//    	System.out.println("INIT LEFT PID");
    	
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
//    	System.out.println("RUNNING LEFT PID");
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
//    	System.out.println("END LEFT PID");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    }
}


