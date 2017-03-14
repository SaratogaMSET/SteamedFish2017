package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurretSubsystem extends PIDSubsystem {

    // Initialize your subsystem here
	public CANTalon turretMotor;
	public DigitalInput turretHalRight;
	public DigitalInput turretHalLeft;
	public AnalogPotentiometer turretABSEncoder;
	public static final double TURRET_CIRCUMFERENCE = 20;
	public static int jumps = 0;
	Timer time;
	public boolean isCloseToUpReset, isCloseToLowReset;
	public double currentEncoderValue;
	public double prevEncoderValue;
	public double currentEncoderTick;
	public PIDController turretTurnPID;
	public static double maxRotationValue = 6.2;
	public static double minRotationValue = -6.2;

	public static class PIDConstantsTurret {
		public static final double ABS_TOLERANCE = 0.02; //1.2 degrees
		public static  double k_P = 1.85; //0.2, 1.0
		public static double k_I = 0.0;
		public static double k_D = 0.01;

	}
    public TurretSubsystem() {
    	super("Turret PID",PIDConstantsTurret.k_P,PIDConstantsTurret.k_I,PIDConstantsTurret.k_D);
    	isCloseToUpReset = false;
    	isCloseToLowReset = false;
    	//turretHalRight = new DigitalInput(RobotMap.Turret.TURRET_HALL_EFFECT_RIGHT_PORT);
    	//turretHalLeft = new DigitalInput(RobotMap.Turret.TURRET_HALL_EFFECT_LEFT_PORT);
    	turretMotor = new CANTalon(RobotMap.Turret.TURRET_MOTOR_PORT);
    	turretABSEncoder = new AnalogPotentiometer(RobotMap.Turret.TURRET_ABS_ENCODER_PORT);
    	
    	currentEncoderValue = 0;
    	prevEncoderValue = getTurretEncoderValue();
    	currentEncoderTick = 0;
    	turretTurnPID = this.getPIDController();
    	turretTurnPID.setAbsoluteTolerance(PIDConstantsTurret.ABS_TOLERANCE);
    	turretTurnPID.setOutputRange(-1.0,1.0);
    	
    }
    public void manualSet(double speed){
    	turretMotor.set(speed);
    }
//    public double getEncoderDistance(){
//    	return turretABSEncoder.getDistance();
//    }
    /*
     * interupt pid of turret if get hal
     * also set encoder to zero
     */
//    public boolean getRightHal(){
//    	return turretHalRight.get();
//    }
//    public boolean getLeftHal(){
//    	return turretHalLeft.get(); 
//    }
    public void resetEncoder(){
    	currentEncoderTick = 0;
    }
    /*
     * Assumes that a positive angle is to the right
     * Positive distance is right
     */
    public double translateAngleToABS(double angle){
    	return angle/60;  
    }
    @Override
   	protected double returnPIDInput() {
   		// TODO Auto-generated method stub
   		return getTotalDist();
   	}
	@Override
    protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("output of turret pid", -output);
    	turretMotor.set(-output);
    }
//	public boolean isOnTarget(double distance) {
//		// TODO Auto-generated method stub
//		return ;
//	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public double getTurretEncoderValue() {
    	return turretABSEncoder.get();
    }
    
    //S&A
    public void turn(double power){
		turretMotor.set(power);
	}


    public void countCurrentPosition(){
    	/*
    	 * save the previous value
    	 * if abs value of the previous difference is greater than 0.5
    	 * then if the pos went down then it goes negative tick
    	 * then if pos went up then increment up
    	 */
    	if(Math.abs(prevEncoderValue - getTurretEncoderValue()) >= 0.5){
    		if(prevEncoderValue > getTurretEncoderValue()){
    			currentEncoderTick += 1;
    		}else{
    			currentEncoderTick -= 1;
    		}
    	}
    	prevEncoderValue = getTurretEncoderValue();
    	
    	if(Robot.turret.getTotalDist() > Robot.turret.maxRotationValue){
    		Robot.isTurretMax = true;
    	}else if(Robot.turret.minRotationValue < Robot.turret.getTotalDist()){
    		Robot.isTurretMin = true;
    	}else{
    		Robot.isTurretMin = false;
    		Robot.isTurretMax = false;

    	}
    	
    	
    }
    public double getTotalDist(){
		return currentEncoderTick+getTurretEncoderValue();
    }
   
}
