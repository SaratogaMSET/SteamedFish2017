package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	public DoubleSolenoid intakeFlapSol;
	public DoubleSolenoid gearSol;
	
	public static final double INTAKE_GEAR_SPEED = 1.0;
	public static final double INTAKE_BALL_SPEED = -0.15;
	
	public GearSubsystem(){
		gearIRBreaker = new DigitalInput(RobotMap.Gear.GEAR_IR_PORT);
		funnelMotor = new CANTalon(RobotMap.Gear.GEAR_ROLLER_PORT);
		gearSol = new DoubleSolenoid(RobotMap.Gear.GEAR_SOL_PORT[0], RobotMap.Gear.GEAR_SOL_PORT[1], RobotMap.Gear.GEAR_SOL_PORT[2]);
		intakeFlapSol = new DoubleSolenoid(RobotMap.Gear.GEAR_FUNNEL_PORT[0],RobotMap.Gear.GEAR_FUNNEL_PORT[1],RobotMap.Gear.GEAR_FUNNEL_PORT[2]);
	}
	
	public boolean isGearLoaded(){
		return gearIRBreaker.get();
	}
	public void setFunnelMotor(double speed){
		funnelMotor.set(speed);
	}
	public void setFunnelPistonState(boolean isGear){
		if(isGear){
			intakeFlapSol.set(DoubleSolenoid.Value.kForward);
		}else{
			intakeFlapSol.set(DoubleSolenoid.Value.kReverse);
		}
	}
	public void setGearFlapSol(boolean isIn){
		if(isIn){
			gearSol.set(DoubleSolenoid.Value.kForward);
		}else{
			gearSol.set(DoubleSolenoid.Value.kReverse);
		}
	}
	public boolean getIntakeFlapPos(){
		if(intakeFlapSol.get() == DoubleSolenoid.Value.kForward){
			return true;
		}else{
			return false;
		}
	}
	public boolean getGearFlapSolPos(){
		if(gearSol.get() == DoubleSolenoid.Value.kForward){
			return false;
		}else{
			return true;
		}
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

