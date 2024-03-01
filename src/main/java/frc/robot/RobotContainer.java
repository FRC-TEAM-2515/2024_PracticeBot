// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
// import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;

//subsystems
import frc.robot.subsystems.DriveTrainSubsystem;

//commands
import frc.robot.commands.ArcadeDriveCmd;
import frc.robot.commands.DriveForwardCmd;

//constants
import static frc.robot.Constants.AutonomousConstants;
import static frc.robot.Constants.OperatorConstants;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DriveTrainSubsystem m_driveTrainSubsystem = new DriveTrainSubsystem();

    private final XboxController m_XboxController = new XboxController(OperatorConstants.kDriverControllerPort);

    private final ArcadeDriveCmd m_arcadeDriveCmd = new ArcadeDriveCmd(m_driveTrainSubsystem, 
                                                        () -> m_XboxController.getLeftY(),
                                                        () -> m_XboxController.getRightX());

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        configureBindings(); // Configure the trigger bindings
        m_driveTrainSubsystem.setDefaultCommand(m_arcadeDriveCmd);
    }

    // /**
    //  * Use this method to define your trigger->command mappings. Triggers can be created via the
    //  * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
    //  * predicate, or via the named factories in {@link
    //  * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
    //  * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
    //  * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
    //  * joysticks}.
    //  */
    private void configureBindings() {
      // // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
      // new Trigger(m_driveTrainSubsystem::exampleCondition)
      //     .onTrue(new ExampleCommand(m_driveTrainSubsystem));

      // // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
      // // cancelling on release.
      // m_driverController.b().whileTrue(m_driveTrainSubsystem.exampleMethodCommand());
      
    }

    // /**
    //  * Use this to pass the autonomous command to the main {@link Robot} class.
    //  *
    //  * @return the command to run in autonomous
    //  */
    public Command getAutonomousCommand() {
        return new DriveForwardCmd(m_driveTrainSubsystem, AutonomousConstants.kAutoDriveDistance);
    }
}
