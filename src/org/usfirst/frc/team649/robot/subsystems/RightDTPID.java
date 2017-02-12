package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class RightDTPID extends PIDSubsystem {

public PIDController encoderDriveRightPID;
    
    
    public RightDTPID() {
    	super("DT Right", 0.6, 0, 0.05);

       	
    	encoderDriveRightPID = this.getPIDController();
    	encoderDriveRightPID.setAbsoluteTolerance(0.8);

    	encoderDriveRightPID.setOutputRange(-0.7, 0.7);
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	@Override
	protected double returnPIDInput() {
		return Robot.drive.getDistanceDTRight();
	}

	@Override
	protected void usePIDOutput(double output) {
		
        Robot.drive.motors[0].set(-output);
        Robot.drive.motors[1].set(-output);
//        Robot.drivetrain.motors[2].set(-output);
//        Robot.drivetrain.motors[3].set(-output);
	}

    @Override
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}


