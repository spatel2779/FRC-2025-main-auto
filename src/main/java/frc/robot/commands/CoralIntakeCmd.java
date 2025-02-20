package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralPlacer;

public class CoralIntakeCmd extends Command{
    public CoralPlacer Intake;
    public double speed;
    public Timer timer;

    public CoralIntakeCmd(CoralPlacer CoralIntake, double spd){
        this.Intake = CoralIntake;
        this.speed = spd;
        addRequirements(Intake);

    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        Intake.Take(speed);

    }

    @Override
    public void end(boolean interrupted) {
        Intake.Take(0);
        timer.stop();
        timer.reset();

    }

    @Override
    public boolean isFinished() {
    if(timer.get()>0.5)
    return true;
    else
    return false;
  }
}
