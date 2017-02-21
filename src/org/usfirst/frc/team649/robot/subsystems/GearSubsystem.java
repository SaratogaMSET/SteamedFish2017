package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	/*
	 * when getting ball or gear from h-player actuate piston
	 * spin motor in for gear out for ball
	 */
	public DigitalInput gearIRBreaker;
	public CANTalon funnelMotor;
	public Solenoid intakeFlapSol;
	public Solenoid gearSol;
	
	public static final double INTAKE_GEAR_SPEED = 1.0;
	public static final double INTAKE_BALL_SPEED = -0.15;
	
	public GearSubsystem(){
//		gearIRBreaker = new DigitalInput(RobotMap.Gear.GEAR_IR_PORT);
		funnelMotor = new CANTalon(RobotMap.Gear.GEAR_ROLLER_PORT);
		gearSol = new Solenoid(RobotMap.Gear.GEAR_SOL_PORT);
	}
	
	public boolean isGearLoaded(){
		return gearIRBreaker.get();
	}
	
	public void setFunnelMotor(double speed){
		funnelMotor.set(speed);
	}
	public void setIntakeFlapPistonState(boolean isGear){
		if(isGear){
			intakeFlapSol.set(true);
		}else{
			intakeFlapSol.set(false);
		}
	}
	public void setGearSol(boolean isOut){
		if(isOut){
			gearSol.set(true);
		}else{
			gearSol.set(false);
		}
	}
	public boolean getIntakeFlapPos(){
		return intakeFlapSol.get();
	}
	public boolean getGearSolPos(){
		return gearSol.get();
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

