package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class TurretSubsystem extends PIDSubsystem {

    // Initialize your subsystem here
	public CANTalon turretMotor;
	public DigitalInput turretHalRight;
	public DigitalInput turretHalLeft;
	public Encoder turretABSEncoder;
	public PIDController encoderTurretPID;
	public static final double TURRET_CIRCUMFERENCE = 20;

	public static class PIDConstantsTurret {
		public static final double ABS_TOLERANCE = 0.25;
		public static  double k_P = .01; //0.2
		public static double k_I = 0;
		public static double k_D = 0.02;
		public static final double DISTANCE_PER_PULSE = 12.56 / 256 * 60.0/14.0; // need gearbox ratio for turret 

	}
    public TurretSubsystem() {
    	super("Turret PID",PIDConstantsTurret.k_P,PIDConstantsTurret.k_I,PIDConstantsTurret.k_D);
    	//turretHalRight = new DigitalInput(RobotMap.Turret.TURRET_HALL_EFFECT_RIGHT_PORT);
    	//turretHalLeft = new DigitalInput(RobotMap.Turret.TURRET_HALL_EFFECT_LEFT_PORT);
    	turretMotor = new CANTalon(RobotMap.Turret.TURRET_MOTOR_PORT);
    	//turretABSEncoder = new Encoder(RobotMap.Turret.TURRET_ABS_ENCODER_PORT[0], RobotMap.Turret.TURRET_ABS_ENCODER_PORT[1]);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    public void manualSet(double speed){
    	turretMotor.set(speed);
    }
    public double getEncoderDistance(){
    	return turretABSEncoder.getDistance();
    }
    /*
     * interupt pid of turret if get hal
     * also set encoder to zero
     */
    public boolean getRightHal(){
    	return turretHalRight.get();
    }
    public boolean getLeftHal(){
    	return turretHalLeft.get(); 
    }
    public void resetEncoder(){
    	turretABSEncoder.reset();
    }
    /*
     * Assumes that a positive angle is to the right
     * Positive distance is right
     */
    public double translateAngleToInches(double angle){
    	return TURRET_CIRCUMFERENCE*angle/360;  
    }
    @Override
   	protected double returnPIDInput() {
   		// TODO Auto-generated method stub
   		return getEncoderDistance();
   	}
	@Override
    protected void usePIDOutput(double output) {
    	turretMotor.set(output);
    }
	public boolean isOnTarget(double distance) {
		// TODO Auto-generated method stub
		return Math.abs(getEncoderDistance() - distance) < TurretSubsystem.PIDConstantsTurret.ABS_TOLERANCE;
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    

   
}
