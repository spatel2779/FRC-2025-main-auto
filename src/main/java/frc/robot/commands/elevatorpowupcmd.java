package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class elevatorpowupcmd extends Command{
    public Elevator elevator;
    public double height;
    public Timer timer;
    public double wait;

    public elevatorpowupcmd(Elevator elevatormove, double move, double time){
        this.elevator = elevatormove;
        this.height = move;
        this.wait = time;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

    }

    @Override
    public void execute() {
        elevator.LElevator.set(0.3);
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setzeropower();
        timer.stop();
        timer.reset();
    }

    @Override
    public boolean isFinished() {
    if ((Math.toDegrees(elevator.encoder.getPosition()) < height +2 && Math.toDegrees(elevator.encoder.getPosition()) > height -2)|| timer.get()>wait){
    return true;
    }else{
    return false;
    }
  }
}
