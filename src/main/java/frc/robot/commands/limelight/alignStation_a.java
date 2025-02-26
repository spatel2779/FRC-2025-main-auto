package frc.robot.commands.limelight;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.limelight.Limelight3DDistance;
import frc.robot.subsystems.CoralPlacer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Elevator;

public class alignStation_a extends Command{
    public Limelight3DDistance llm;
    public Elevator ele;
    public CoralPlacer coral;
    public DriveSubsystem m_robotDrive;
    public Timer timer;
    public double temptimer;
    public double wait;

    public alignStation_a(Limelight3DDistance llm1, DriveSubsystem roboDrive,double wait){
        this.llm = llm1;
        this.m_robotDrive = roboDrive;
        this.wait = wait;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        llm.reeflimelightA(m_robotDrive);
        
    }

    @Override
    public void end(boolean interrupted) {
        llm.stoprobot(m_robotDrive);

    }

    @Override
    public boolean isFinished() {
    if (timer.get()>wait){
        System.out.println("Limelight A done");
        return true;
        }
    else{
        return false;
        }
    }
}
