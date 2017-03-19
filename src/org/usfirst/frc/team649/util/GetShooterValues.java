package org.usfirst.frc.team649.util;

public class GetShooterValues {
	public static double returnShooterMinPower(int RPMValue){
		int RPM = RPMValue;
		if(RPM % 50 != 0){
			RPM -= 25;
		}
		if(RPM == 1100){
			return 0.39;
		}else if(RPM == 1150){
			return 0.41;
		}else if(RPM == 1200){
			return 0.425;
		}else if(RPM == 1250){
			return 0.44;
		}else if(RPM == 1300){
			return 0.451;
		}else if(RPM == 1350){
			return 0.476;
		}else if(RPM == 1400){
			return 0.492;
		}else if(RPM == 1450){
			return 0.512;
		}else if(RPM == 1500){
			return 0.523;
		}else if(RPM == 1550){
			return 0.539;
		}else if(RPM == 1600){
			return 0.555;
		}else if(RPM == 1650){
			return 0.571;
		}else if(RPM == 1700){
			return 0.59;
		}else if(RPM == 1750){
			return 0.605;
		}else if(RPM == 1800){
			return 0.628;
		}else if(RPM == 1850){
			return 0.637;
		}else if(RPM == 1900){
			return 0.66;
		}else if(RPM == 1950){
			return 0.676;
		}else{
			return 0.701;
		}
	}
	public static double returnShooterMaxPower(int RPM){
		if(RPM % 50 != 0){
			RPM += 25;
		}
		if(RPM == 1100){
			return 0.425;
		}else if(RPM == 1150){
			return 0.44;
		}else if(RPM == 1200){
			return 0.451;
		}else if(RPM == 1250){
			return 0.476;
		}else if(RPM == 1300){
			return 0.492;
		}else if(RPM == 1350){
			return 0.512;
		}else if(RPM == 1400){
			return 0.523;
		}else if(RPM == 1450){
			return 0.539;
		}else if(RPM == 1500){
			return 0.555;
		}else if(RPM == 1550){
			return 0.571;
		}else if(RPM == 1600){
			return 0.59;
		}else if(RPM == 1650){
			return 0.605;
		}else if(RPM == 1700){
			return 0.628;
		}else if(RPM == 1750){
			return 0.637;
		}else if(RPM == 1800){
			return 0.66;
		}else if(RPM == 1850){
			return 0.676;
		}else if(RPM == 1900){
			return 0.701;
		}else if(RPM == 1950){
			return 0.711;
		}else{
			return 0.721;
		}
	}
}
