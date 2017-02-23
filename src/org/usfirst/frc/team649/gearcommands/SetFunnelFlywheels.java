package org.usfirst.frc.team649.gearcommands;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetFunnelFlywheels extends Command {
	//1 = ball
	//2 = gear
	//3 = stop
	int state;
    public SetFunnelFlywheels(int state) {
        // eg. requires(chassis);
    	this.state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(state == 1){
        	Robot.gear.setFunnelMotor(Robot.gear.INTAKE_BALL_SPEED);
    	}else if(state == 2){
    		Robot.gear.setFunnelMotor(Robot.gear.INTAKE_GEAR_SPEED);
    	}else{
    		Robot.gear.setFunnelMotor(0.0);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
