package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.wildstang.framework.core.Core;
import org.wildstang.framework.core.Subsystems;
import org.wildstang.framework.io.Input;
import org.wildstang.framework.io.inputs.AnalogInput;
import org.wildstang.framework.io.inputs.DigitalInput;
import org.wildstang.framework.subsystems.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.CANConstants;
import frc.robot.SBInputs;

public class Climber implements Subsystem {
    //inputs
    private DigitalInput climberUpButton1;
    private DigitalInput climberDownButton1;
    private DigitalInput climberUpButton2;
    private DigitalInput climberDownButton2;

    //outputs
    private TalonFX climber1;
    private TalonFX climber2;
    //states
    private double runSpeed = .9;

    //commands
    private boolean commandClimberUpButton1 = false;
    private boolean commandClimberDownButton1 = false;
    private boolean commandClimberUpButton2 = false;
    private boolean commandClimberDownButton2 = false;

    @Override
    public void inputUpdate(Input source) {
        commandClimberUpButton1 = climberUpButton1.getValue();
        commandClimberDownButton1 = climberDownButton1.getValue();
        commandClimberUpButton2 = climberUpButton2.getValue();
        commandClimberDownButton2 = climberDownButton2.getValue();

        
    }

    @Override
    public void init() {
        climber1 = new TalonFX(CANConstants.FIRST_CLIMBER);
        climber2 = new TalonFX(CANConstants.SECOND_CLIMBER);

        climberUpButton1 = (DigitalInput) Core.getInputManager().getInput(SBInputs.DRIVER_FACE_RIGHT.getName());
        climberUpButton1.addInputListener(this);
        climberDownButton1 = (DigitalInput) Core.getInputManager().getInput(SBInputs.DRIVER_FACE_DOWN.getName());
        climberDownButton1.addInputListener(this);
        climberUpButton2 = (DigitalInput) Core.getInputManager().getInput(SBInputs.DRIVER_FACE_UP.getName());
        climberUpButton2.addInputListener(this);
        climberDownButton2 = (DigitalInput) Core.getInputManager().getInput(SBInputs.DRIVER_FACE_LEFT.getName());
        climberDownButton2.addInputListener(this);

    }

    @Override
    public void selfTest() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        SmartDashboard.putBoolean("Climber One Command Up", commandClimberUpButton1);
        SmartDashboard.putBoolean("Climber One Command Down", commandClimberDownButton1);
        SmartDashboard.putBoolean("Climber Two Command Up", commandClimberUpButton2);
        SmartDashboard.putBoolean("Climber Two Command Down", commandClimberDownButton2);
        //climber.set(ControlMode.PercentOutput,  runSpeed); 
        if(commandClimberDownButton1){
            climber1.set(ControlMode.PercentOutput,  runSpeed*-1);  
        }
        if(commandClimberUpButton1) {
            climber1.set(ControlMode.PercentOutput,  runSpeed);  
        }
        if(!commandClimberDownButton1 && !commandClimberUpButton1){
            climber1.set(ControlMode.PercentOutput,  0); 
        }
        if(commandClimberDownButton2){
            climber2.set(ControlMode.PercentOutput,  runSpeed*-1);  
        }
        if(commandClimberUpButton2) {
            climber2.set(ControlMode.PercentOutput,  runSpeed);  
        }
        if(!commandClimberDownButton2 && !commandClimberUpButton2){
            climber2.set(ControlMode.PercentOutput,  0); 
        }
    }

    @Override
    public void resetState() {
        
        
    }

    @Override
    public String getName() {
        
        return "CLIMBER";
    }
    
}
