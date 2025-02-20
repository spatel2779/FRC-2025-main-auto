package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.sensor.algaesense;
import frc.robot.subsystems.algae;

public class algaeintakecmd extends Command{
    public algae algae_s;
    public Timer timer;
    public algaesense sensor;
    public algaeintakecmd(algae Algae, algaesense sensing){
      this.algae_s = Algae;
      this.sensor = sensing;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        algae_s.Take(-0.6);

    }

    @Override
    public void end(boolean interrupted) {
        algae_s.Take(0.0);
        timer.stop();
        timer.reset();

    }

    @Override
    public boolean isFinished() {
    if(timer.get()>3 || !sensor.input.get())
    return true;
    else
    return false;
  }
}
