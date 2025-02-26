package frc.robot.commands.coral;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralPlacer;

public class CoralIntakeCmd extends Command{
    public CoralPlacer coral_s;
    public Timer timer;
    public double wait;
    public CoralIntakeCmd(CoralPlacer coral, double time){
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
        coral_s.Take(0.6);

    }

    @Override
    public void end(boolean interrupted) {
        coral_s.Take(0);
        timer.stop();
        timer.reset();

    }

    @Override
    public boolean isFinished() {
    if(timer.get()>wait){
    System.out.println("Coral intake finished");
    return true;
    }else{
    return false;
    }
  }
}
