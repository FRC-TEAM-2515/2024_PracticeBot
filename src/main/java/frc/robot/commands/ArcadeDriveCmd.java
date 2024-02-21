// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.ArcadeDriveConstants;
import frc.robot.subsystems.DriveTrainSubsystem;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ArcadeDriveCmd extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveTrainSubsystem m_driveTrainSubsystem;
    private final Supplier<Double> m_speedFunction, m_turnFunction;

    // /**
    //  * Creates a new ExampleCommand.
    //  *
    //  * @param subsystem The subsystem used by this command.
    //  */
    public ArcadeDriveCmd(DriveTrainSubsystem driveTrainSubsystem, Supplier<Double> speedFunction, 
                                                                   Supplier<Double> turnFuntion) {
        m_speedFunction = speedFunction;
        m_turnFunction = turnFuntion;
        m_driveTrainSubsystem = driveTrainSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_driveTrainSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double realTimeSpeed = m_speedFunction.get();
        double realTimeTurn = m_turnFunction.get();

        m_driveTrainSubsystem.drive(ArcadeDriveConstants.kDriveSpeedMultiplier*realTimeSpeed, realTimeTurn);

        SmartDashboard.putBoolean("Driving Forward: ", true);
        System.out.println("Driving " + "realTimeSpeed " + realTimeSpeed + "realTimeTurn " + realTimeTurn);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_driveTrainSubsystem.drive(0, 0);
        SmartDashboard.putBoolean("Driving Forward: ", false);
        System.out.println("Not Driving");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
