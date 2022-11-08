package frc.auto.steps;

import org.wildstang.framework.CoreUtils;
import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;

import frc.robot.SBSubsystems;
import frc.subsystems.Shooter;

public class StopShooter extends AutoStep{
    Shooter shooterSubSystem;
    
    @Override
    public void initialize() {
        shooterSubSystem = (Shooter) Core.getSubsystemManager().getSubsystem(SBSubsystems.SHOOTER);
        
    }

    @Override
    public void update() {
        shooterSubSystem.shooterOff();
        setFinished(true);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "ShootBallInGoal";
    }

    
}
