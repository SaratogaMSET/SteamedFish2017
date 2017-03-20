package org.usfirst.frc.team649.autonomousSequences;

import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.AllianceSelector;
import org.usfirst.frc.team649.drivetrain.DrivetrainSubsystem.AutoConstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.hal.AllianceStationID;

public class AutoFullSequence extends CommandGroup {
	public String pos;
	public String goal;
	public String alliance;

	public AutoFullSequence(int position, int program, int alliance) {
		System.out.println("\n");

		if (program == AutoConstants.DO_NOTHING) {
			System.out.println("DOING NOTHING");
			updateAutoSDPos("NOTHING");
			updateAutoSDGoal("No Goal!");
			return;
		}
		
		if (program == AutoConstants.FAR) {
			updateAutoSDPos("Far Side");
			updateAutoSDGoal("Far Side Gear and Shooting!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideGearShootFarSide());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideGearShootFarSide());
			}
		}
		
		if (program == AutoConstants.MIDDLE) {
			updateAutoSDPos("Position 2");
			updateAutoSDGoal("Middle Gear and Shooting!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideGearShootMiddle());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideGearShootMiddle());
			}
		}
		
		if (program == AutoConstants.BOILER) {
			updateAutoSDPos("Boiler Side");
			updateAutoSDGoal("Boiler Side Gear and Shooting!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideGearShootFarSide());
			} else if(alliance == AllianceSelector.RED) {
				addSequential(new RedSideGearShootFarSide());
			}
		}
		
		if (program == AutoConstants.HOPPER) {
			updateAutoSDPos("Boiler Side");
			updateAutoSDGoal("Hopper!");
			if (alliance == AllianceSelector.BLUE) {
				addSequential(new BlueSideHopperOnlyBoilerSide());
			} else if (alliance == AllianceSelector.RED) {
				addSequential(new RedSideHopperOnlyBoilerSide());
			}
		}
		if (program == AutoConstants.HOPPERANDGEAR) {
			updateAutoSDPos("Boiler Side");
			updateAutoSDGoal("Hopper and Gear!");
			if (alliance == AllianceSelector.BLUE) {
//				addSequential(new BlueSideHopperGearBoilerSide());
			} else if (alliance == AllianceSelector.RED) {
//				addSequential(new RedSideHopperGearBoilerSide());
			}
		}

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
