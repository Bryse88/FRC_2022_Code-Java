package frc.subsystems;

import org.wildstang.framework.io.Input;
import org.wildstang.framework.subsystems.Subsystem;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoAdl implements Subsystem {

    private NetworkTable netTable;
    private NetworkTableEntry txEntry;
    @Override
    public void inputUpdate(Input source) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        initInputs();
        resetState();
    }
    private void initInputs(){
        netTable = NetworkTableInstance.getDefault().getTable("limelight");
        //Auto = (DigitalInput) Core.getInputManager().getInput(SBInputs.DRIVER_FACE_RIGHT.getName());
        //climberUpButton1.addInputListener(this);
    }

    @Override
    public void selfTest() {
        // TODO Auto-generated method stub
        
    }
    public double getTXValue() {
        return txEntry.getDouble(0.0);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resetState() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
