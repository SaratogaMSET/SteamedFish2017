package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GyroSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	AnalogGyro gyro;
	
	public GyroSubsystem() {
		gyro = new AnalogGyro(RobotMap.Drivetrain.GYRO_PORT);
		
	}
	
	public double getAngle() {
		return gyro.getAngle();
	}
	
	public void resetGyro() {
		gyro.reset();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

