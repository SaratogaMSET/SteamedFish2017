package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.AllianceSelector;
import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.AutoConstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.hal.AllianceStationID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoFullSequence extends CommandGroup {
	public String pos;
	public String goal;
	public String alliance;

	public AutoFullSequence(int program, int alliance) {
		System.out.println("\n");

		if (program == AutoConstants.DO_NOTHING) {
			System.out.println("Drive Fwd");
			updateAutoSDPos("NOTHING");
			updateAutoSDGoal("No Goal!");
			addSequential(new DriveForwardAuto());
		}
		
		if (program == AutoConstants.FAR) {
			updateAutoSDPos("Far Side");
			updateAutoSDGoal("Far Side Gear and Shooting!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideGearFarSide());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideGearFarSide());
			} else if(alliance == AllianceSelector.RED_NO_SHOOT){
				addSequential(new RedSideGearFarSide());
			}else if(alliance == AllianceSelector.BLUE_NO_SHOOT){
				addSequential(new BlueSideGearFarSide());
			}
		}
		
		if (program == AutoConstants.MIDDLE) {
			updateAutoSDPos("Position 2");
			updateAutoSDGoal("Middle Gear and Shooting!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideGearShootMiddle());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideGearShootMiddle());
			}else if(alliance == AllianceSelector.RED_NO_SHOOT){
				addSequential(new RedSideGearNoShootMiddle());
			}else if(alliance == AllianceSelector.BLUE_NO_SHOOT){
				addSequential(new BlueSideGearNoShootMiddle());
			}
		}
		
		if (program == AutoConstants.BOILER) {
			updateAutoSDPos("Boiler Side");
			updateAutoSDGoal("Boiler Side Gear and Shooting!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideBoilerGearShoot());
			} else if(alliance == AllianceSelector.RED) {
				addSequential(new RedSideBoilerGearShoot());
			}else if(alliance == AllianceSelector.RED_NO_SHOOT){
				addSequential(new RedSideBoilerGearNoShoot());
			}else if(alliance == AllianceSelector.BLUE_NO_SHOOT){
				addSequential(new BlueSideBoilerGearNoShoot());
			}
		}
		
		if (program == AutoConstants.HOPPER) {
			updateAutoSDPos("Boiler Side");
			updateAutoSDGoal("Hopper!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideHopperOnlyBoilerSide());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideHopperOnlyBoilerSide());
			}else if(alliance == AllianceSelector.RED_NO_SHOOT){
				addSequential(new RedSideHopperOnlyBoilerSide());
			}else if(alliance == AllianceSelector.BLUE_NO_SHOOT){
				addSequential(new BlueSideHopperOnlyBoilerSide());
			}
		}
		if (program == AutoConstants.FOWARD) {
			updateAutoSDPos("Boiler Side");
			addSequential(new DriveForwardAuto());
		}
		
//		SmartDashboard.putString("Position", getPos());
//		SmartDashboard.putString("Goal", getGoal());

	}

	public void updateAutoSDPos(String state) {
		pos = state;
	}

	public void updateAutoSDGoal(String state) {
		goal = state;
	}
	
	public void updateAutoAlliance(String state) {
		alliance = state;
	}

	public String getPos() {
		return pos;
	}

	public String getGoal() {
		return goal;
	}

}
