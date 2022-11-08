package frc.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;

import frc.robot.SBSubsystems;
import frc.subsystems.Shooter;

public class StartShooterToSpeed extends AutoStep{
    Shooter intakeSubSystem;
    double rpmRequest;
    public StartShooterToSpeed(double rpm){
        rpmRequest = rpm;
    }

    @Override
    public void initialize() {
        intakeSubSystem = (Shooter) Core.getSubsystemManager().getSubsystem(SBSubsystems.SHOOTER);
        
    }

    @Override
    public void update() {
        intakeSubSystem.shooterOn(rpmRequest);
        setFinished(true);
    }

    @Override
    public String toString() {
        return "ShootBallInGoal";
    }

    
}
