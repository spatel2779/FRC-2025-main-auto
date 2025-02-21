// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.events.EventTrigger;

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
import frc.robot.commands.Gimbalcmd;
import frc.robot.commands.algaeintakecmd;
import frc.robot.commands.algaeintaketime;
import frc.robot.commands.alignStation;

public class RobotContainer {

  public final XboxController Driver_1 = new XboxController(0);
  public final XboxController Driver_2 = new XboxController(1);

  public static Elevator elevator;
  private static DriveSubsystem m_robotDrive ;
  public static Gimbal gimbal;
  public static algae algae;
  public static Limelight3DDistance aprilDistance;
  public static CoralPlacer coral;
  public static metal elecorr;
  public static algaesense algaeSensor;

  private final SendableChooser<Command> autoChooser;
  
  //Commands
  public static alignStation align;

  public RobotContainer(){
    
    algae = new algae(); 
    elecorr = new metal();
    elevator = new Elevator();
    m_robotDrive = new DriveSubsystem();
    algaeSensor = new algaesense();
    gimbal = new Gimbal();
    coral = new CoralPlacer();
    aprilDistance = new Limelight3DDistance();
    align = new alignStation(aprilDistance, m_robotDrive);
    
    NamedCommands.registerCommand("Algae_in", new algaeintaketime(algae,2)); 
    NamedCommands.registerCommand("Gim_L3", new Gimbalcmd(gimbal, 185));

    autoChooser = AutoBuilder.buildAutoChooser("fwd");
    SmartDashboard.putData("Auto Chooser", autoChooser);

    // NamedCommands.registerCommand("aligntoreef", new llaligntoreef(aprilDistance, m_robotDrive));
    // NamedCommands.registerCommand("aligntostation", new alignStation(aprilDistance, m_robotDrive));
    // NamedCommands.registerCommand("Ele_L4", new elevatorcmd(elevator, 63));
    // NamedCommands.registerCommand("Ele_L3", new elevatorcmd(elevator, 30));
    // NamedCommands.registerCommand("Ele_L2", new elevatorcmd(elevator, 10));
    // NamedCommands.registerCommand("Gim_L4", new Gimbalcmd(gimbal, 185));
    // NamedCommands.registerCommand("Gim_L2", new Gimbalcmd(gimbal, 110));
    // NamedCommands.registerCommand("Gim_ground", new Gimbalcmd(gimbal, 25));    
  
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
    
    new JoystickButton(Driver_1, Button.kSquare.value)
    .onTrue(new alignStation(aprilDistance, m_robotDrive));
  
    /*Mechanism Control for Driver 2*/

    //POV for Elevator position 
    //ground
    new POVButton(Driver_2, 180).onTrue(
      new levels(elevator, gimbal, 3, 100,0.5));
    //L2
    new POVButton(Driver_2, 270).onTrue(
      // new RunCommand(()->elevator.degele(10)));
      new levelsecondbase(elevator, gimbal, 10, 195, 0.5));
    //L3
    new POVButton(Driver_2, 0).onTrue(
      new levels(elevator, gimbal, 30, 195,0.5));
    //L4
    new POVButton(Driver_2, 90).onTrue(
      new levels(elevator, gimbal, 57, 195,0.5));

    // Coral Presets
    new JoystickButton(Driver_2, Button.kOptions.value)
    .onTrue(new RunCommand(()-> gimbal.gimbaldeg(12)));

    //manual gimbal control 
    new Trigger(() -> Driver_2.getRightY()<-0.1).whileTrue(
      new RunCommand(() -> gimbal.gimbalpowup(Math.abs(Driver_2.getRightY()),elevator), gimbal));

    new Trigger(() -> Driver_2.getRightY()>0.1).whileTrue(
      new RunCommand(() -> gimbal.gimbalpowdown(Driver_2.getRightY()), gimbal));


    //L2 algae intake
    new JoystickButton(Driver_2, Button.kL1.value).onTrue(
      new RunCommand(()->gimbal.gimbaldeg(110), gimbal));//tested
    //L3 algae intake
    new JoystickButton(Driver_2, Button.kR1.value).onTrue(
      new levels(elevator, gimbal, 25, 110,0.5));//tested


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
