package org.usfirst.frc.team649.gearcommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunFunnelWheelsForBalls extends Command {

    public RunFunnelWheelsForBalls() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gear.setFunnelMotor(-1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.gear.isGearLoaded();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gear.setFunnelMotor(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
