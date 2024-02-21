// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.ArcadeDriveConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveForwardCmd extends Command {

    private final DriveTrainSubsystem m_driveTrainSubsystem;
    private final double m_distance;
    private double encoderSetpoint;

    /** Creates a new DriveForwardCmd. */
    public DriveForwardCmd(DriveTrainSubsystem driveTrainSubsystem, double distance) {
        m_driveTrainSubsystem = driveTrainSubsystem;
        m_distance = distance;
        addRequirements(driveTrainSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        encoderSetpoint = m_driveTrainSubsystem.getEncoderMeters() + m_distance;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_driveTrainSubsystem.drive(ArcadeDriveConstants.kDriveForwardSpeed, 0);
        SmartDashboard.putBoolean("Auto Driving Forward: ", true);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_driveTrainSubsystem.drive(0, 0);
        SmartDashboard.putBoolean("Auto Driving Forward: ", false);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (m_driveTrainSubsystem.getEncoderMeters() > encoderSetpoint) {
            SmartDashboard.putBoolean("Auto Driving Forward: ", false);
            return true;
        } else
            return false;
    }
}
