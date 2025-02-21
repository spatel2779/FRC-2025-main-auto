package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralPlacer;

public class CoralOutCmd extends Command{
    public CoralPlacer coral_s;
    public Timer timer;
    public double wait;
    public CoralOutCmd(CoralPlacer coral, double time){
      this.coral_s = coral;
      this.wait = time;
      addRequirements(coral);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        coral_s.Take(-0.6);

    }

    @Override
    public void end(boolean interrupted) {
        coral_s.Take(0);
        timer.stop();
        timer.reset();

    }

    @Override
    public boolean isFinished() {
    if(timer.get()>wait)
    return true;
    else
    return false;
  }
}
