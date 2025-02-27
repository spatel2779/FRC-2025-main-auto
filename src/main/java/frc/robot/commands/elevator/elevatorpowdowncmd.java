package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class elevatorpowdowncmd extends Command{
    public Elevator elevator;
    public double height;
    public Timer timer;

    public elevatorpowdowncmd(Elevator elevatormove, double move, double time){
        this.elevator = elevatormove;
        this.height = move;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        elevator.LElevator.set(-0.2);
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setzeropower();
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
    if (elevator.encoder.getPosition() < height){
        System.out.println("Elevator powered down");
    return true;
    }else{
    return false;
    }
  }
}
