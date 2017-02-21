package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class HopperSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CANTalon rightAgitator;
	CANTalon leftAgitator;
	
	public HopperSubsystem() {
		rightAgitator = new CANTalon(RobotMap.Hopper.RIGHT_AGITATOR);
		leftAgitator = new CANTalon(RobotMap.Hopper.LEFT_AGITATOR);
	}
	
	public void setLeftPower(double power) {
		leftAgitator.set(power);
	}
	public void setRightPower(double power) {
		rightAgitator.set(power);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

