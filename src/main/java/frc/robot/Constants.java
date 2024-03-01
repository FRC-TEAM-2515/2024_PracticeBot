// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class DriveTrainConstants {
    public static final double kWheelDiameterInches = 6;
    public static final double kGearRatio = 8.45;
    public static final int kUnitsPerRev = 4096; 
    public static final double kRampRate = 4.023;
  }
  public static class ArcadeDriveConstants {
    public static final double kDriveSpeedMultiplier = 0.5;
    public static final double kTurnSpeedMultiplier = 0.5;
  }
  public static class AutonomousConstants {
    public static final double kAutoDriveDistance = 5.0; //meters
    public static final double kAutoDriveSpeed = 0.5;
  }
}
