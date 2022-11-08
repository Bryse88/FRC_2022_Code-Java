package frc.auto.steps;

import org.wildstang.framework.auto.steps.AutoStep;
import org.wildstang.framework.core.Core;

import frc.robot.SBSubsystems;
import frc.subsystems.Intake;

public class StopIntake  extends AutoStep{
    Intake intakeSubSystem;

    @Override
    public void initialize() {
        intakeSubSystem = (Intake) Core.getSubsystemManager().getSubsystem(SBSubsystems.INTAKE);
    }

    @Override
    public void update() {
        intakeSubSystem.turnIntakeOff(false);
        setFinished(true);
    }

    @Override
    public String toString() {
        return "StartIntake";
    }
    
}
