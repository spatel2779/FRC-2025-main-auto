// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.CoralPlacer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Gimbal;
import frc.robot.subsystems.algae;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.elevatorcmd;
import frc.robot.commands.levels;
import frc.robot.commands.levelsecondbase;
import frc.robot.commands.llaligntoreef;
import frc.robot.limelight.Limelight3DDistance;
import frc.robot.sensor.algaesense;
import frc.robot.sensor.metal;
import frc.robot.commands.CoralIntakeCmd;
import frc.robot.commands.algaeintakecmd;
import frc.robot.commands.alignStation;

public class RobotContainer {

  public final XboxController Driver_1 = new XboxController(0);
  public final XboxController Driver_2 = new XboxController(1);

  public final Elevator elevator = new Elevator();
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  public final Gimbal gimbal = new Gimbal();
  public final algae algae = new algae();
  public final Limelight3DDistance aprilDistance = new Limelight3DDistance();
  public final CoralPlacer coral = new CoralPlacer();
  public final metal elecorr = new metal();
  public final algaesense algaeSensor = new algaesense();

  private final SendableChooser<Command> autoChooser;
  
  //Commands
  private final alignStation align = new alignStation(coral, elevator, aprilDistance, m_robotDrive);

  public RobotContainer(){
    autoChooser = AutoBuilder.buildAutoChooser("fwd");
    SmartDashboard.putData("Auto Chooser", autoChooser);
    NamedCommands.registerCommand("aligntoreef", new llaligntoreef(aprilDistance, m_robotDrive));
  
    configureBindings();  
    m_robotDrive.setDefaultCommand(
      new RunCommand(
        () -> m_robotDrive.drive(
          -MathUtil.applyDeadband(Driver_1.getLeftY(),0.06),
          -MathUtil.applyDeadband(Driver_1.getLeftX(),0.06),
          -MathUtil.applyDeadband(Driver_1.getRightX(),0.06),
          true,false),
          m_robotDrive));
  }
   
  private void configureBindings() {
    //Driver Controls for Driver 1
    new JoystickButton(Driver_1, Button.kR1.value)
    .whileTrue(new RunCommand(
      () -> m_robotDrive.setX(),
      m_robotDrive));

    new JoystickButton(Driver_1, Button.kTriangle.value)
    .whileTrue(new RunCommand(
      () -> m_robotDrive.driveresetHeading(),
      m_robotDrive));

    new JoystickButton(Driver_1, Button.kCircle.value)
    .whileTrue(new RunCommand(
      ()-> aprilDistance.reeflimelightA(m_robotDrive, Driver_1.getRightTriggerAxis())));
    
      new JoystickButton(Driver_1, Button.kCross.value)
      .whileTrue(new RunCommand(
        ()-> aprilDistance.reeflimelightB(m_robotDrive, Driver_1.getRightTriggerAxis())));
  
    /*Mechanism Control for Driver 2*/

    //POV for Elevator position 
    //ground
    new POVButton(Driver_2, 180).onTrue(
      new levels(elevator, gimbal, 3, 90,0.5));
    //L2
    new POVButton(Driver_2, 270).onTrue(
      // new RunCommand(()->elevator.degele(10)));
      new levelsecondbase(elevator, gimbal, 10, 185, 0.5));
    //L3
    new POVButton(Driver_2, 0).onTrue(
      new levels(elevator, gimbal, 30, 185,0.5));
    //L4
    new POVButton(Driver_2, 90).onTrue(
      new levels(elevator, gimbal, 63, 185,0.5));


    //manual gimbal control 
    new Trigger(() -> Driver_2.getRightY()<-0.1).whileTrue(
      new RunCommand(() -> gimbal.gimbalpowup(Math.abs(Driver_2.getRightY()),elevator), gimbal));

    new Trigger(() -> Driver_2.getRightY()>0.1).whileTrue(
      new RunCommand(() -> gimbal.gimbalpowdown(Driver_2.getRightY()), gimbal));


    //L2 algae intake
    new JoystickButton(Driver_2, Button.kL1.value).onTrue(
      new RunCommand(()->gimbal.gimbaldeg(100), gimbal));//tested
    //L3 algae intake
    new JoystickButton(Driver_2, Button.kR1.value).onTrue(
      new levels(elevator, gimbal, 25, 100,0.5));//tested


    // new JoystickButton(Driver_1, Button.kSquare.value)
    // .whileTrue(new RunCommand(() -> aprilDistance.reeflimelight(m_robotDrive), aprilDistance));


    //buttonfor coral intake
    new JoystickButton(Driver_2, Button.kCross.value).onTrue(
      new CoralIntakeCmd(coral, 0.7));//Red Right in
    //button for coral outtake
    new JoystickButton(Driver_2, Button.kCircle.value).whileTrue(
      new RunCommand(() -> coral.Take(-0.6),coral)); //blue left out
    

    //button for algae intake
    // new JoystickButton(Driver_2, Button.kSquare.value).whileTrue(
    //   new RunCommand(() -> algae.Take(-0.6),algae)); // Green  
    //button for algae outtake
    new JoystickButton(Driver_2, Button.kTriangle.value).whileTrue(
      new RunCommand(() -> algae.Take(1),algae));//yellow
 

    //manual elevator control 
    new Trigger(() -> Math.abs(Driver_2.getLeftTriggerAxis())>0.1)
    .whileTrue(new RunCommand(() -> elevator.ElevatorDown(gimbal, Driver_2.getLeftTriggerAxis()), elevator));
    
    new Trigger(() -> Math.abs(Driver_2.getRightTriggerAxis())>0.1)
    .whileTrue(new RunCommand(() -> elevator.ElevatorUP(gimbal, Driver_2.getRightTriggerAxis()), elevator));
    }

  public Command getAutonomousCommand() {

    return new SequentialCommandGroup( autoChooser.getSelected());
  }
  
}
