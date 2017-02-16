package org.usfirst.frc.team649.shootercommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterPID extends Command {
	double distance;
	double tolerance = 0.2;
	public PIDController turretPID;
    public ShooterPID(double angle) {
    	requires(Robot.turret);
    	turretPID = Robot.turret.getPIDController();
    	distance = Robot.turret.translateAngleToInches(angle);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	turretPID.enable();
    	Robot.isTurretPIDActive = true;
    	double setpoint = Robot.turret.getEncoderDistance() + distance;
    	turretPID.setSetpoint(setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.turret.getLeftHal() || Robot.turret.getRightHal() || turretPID.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.turret.manualSet(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
