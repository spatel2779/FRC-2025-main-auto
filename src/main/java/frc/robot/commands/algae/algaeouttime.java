package frc.robot.commands.algae;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.algae;

public class algaeouttime extends Command{
    public algae algae_s;
    public Timer timer;
    public double wait;
    public algaeouttime(algae Algae, double time){
      this.algae_s = Algae;
      this.wait = time;
      addRequirements(Algae);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        algae_s.Take(1);

    }

    @Override
    public void end(boolean interrupted) {
        algae_s.Take(0);
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
