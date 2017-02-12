package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public DigitalInput gearIRBreaker;
	public CANTalon funnelMotor;
	
	public GearSubsystem(){
		gearIRBreaker = new DigitalInput(RobotMap.Gear.GEAR_IR_PORT);
		funnelMotor = new CANTalon(RobotMap.Gear.GEAR_ROLLER_PORT);
	}
	
	public boolean isGearLoaded(){
		return gearIRBreaker.get();
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

