package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class HangSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Solenoid hook;
	public DigitalInput hangLimit;
	public CANTalon hangMiniSim;
	public static final double HANG_POWER = 1.0;
	public HangSubsystem(){
		//hook = new Solenoid(RobotMap.Hanger.HANGER_PISTON);
		hangMiniSim = new CANTalon(RobotMap.Hanger.HANGER_MOTOR_PORT);
		//hangLimit = new DigitalInput(RobotMap.Hanger.HANGER_LIMIT_SWITCH_PORT);
		
	}
	public void setHookSol(boolean in){
		hook.set(in);
	}
	public void setHangMotor(double power){
		hangMiniSim.set(power);
	}
	public boolean getHangLimit(){
		return hangLimit.get();
	}
	public boolean getHookSol(){
		return hook.get();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

