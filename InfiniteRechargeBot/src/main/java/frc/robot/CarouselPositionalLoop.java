

/*

.______   .______          ___   ____    ____  _______ .______     ______   .___________.    _______.
|   _  \  |   _  \        /   \  \   \  /   / |   ____||   _  \   /  __  \  |           |   /       |
|  |_)  | |  |_)  |      /  ^  \  \   \/   /  |  |__   |  |_)  | |  |  |  | `---|  |----`  |   (----`
|   _  <  |      /      /  /_\  \  \      /   |   __|  |   _  <  |  |  |  |     |  |        \   \    
|  |_)  | |  |\  \----./  _____  \  \    /    |  |____ |  |_)  | |  `--'  |     |  |    .----)   |   
|______/  | _| `._____/__/     \__\  \__/     |_______||______/   \______/      |__|    |_______/    
                                                                                                     
*/


package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

public class Position{

    //Designate the motor controllers and XBOX Controller
    
    private CANSparkMax m_left;

    private CANPIDController m_pidController;
   
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    private CANEncoder m_encoder;

    public Position(CANSparkMax l)
    {
        m_left = l;
        m_encoder = m_left.getEncoder();
    }


    public double getRotations()
    {
        return  0.01;//rotations;
    }

    public void initializePositionalPID()
    {
        m_pidController = m_left.getPIDController();
       
     // PID coefficients
     kP = 0.2; 
     kI =0;
     kD = 0; 
     kIz = 0; 
     kFF = 0; 
     kMaxOutput = 1; 
     kMinOutput = -1;
 
     // set PID coefficients
     m_pidController.setP(kP);
     m_pidController.setI(kI);
     m_pidController.setD(kD);
     m_pidController.setIZone(kIz);
     m_pidController.setFF(kFF);
     m_pidController.setOutputRange(kMinOutput, kMaxOutput);
 
     // display PID coefficients on SmartDashboard
     SmartDashboard.putNumber("P Gain", kP);
     SmartDashboard.putNumber("I Gain", kI);
     SmartDashboard.putNumber("D Gain", kD);
     SmartDashboard.putNumber("I Zone", kIz);
     SmartDashboard.putNumber("Feed Forward", kFF);
     SmartDashboard.putNumber("Max Output", kMaxOutput);
     SmartDashboard.putNumber("Min Output", kMinOutput);
     SmartDashboard.putNumber("Set Rotations", 0);
     SmartDashboard.putNumber("ProcessVariable", m_encoder.getPosition());

     }
 
    private void activatePositionalLoop()
    {
     // read PID coefficients from SmartDashboard
     double p = SmartDashboard.getNumber("P Gain", 0);
     double i = SmartDashboard.getNumber("I Gain", 0);
     double d = SmartDashboard.getNumber("D Gain", 0);
     double iz = SmartDashboard.getNumber("I Zone", 0);
     double ff = SmartDashboard.getNumber("Feed Forward", 0);
     double max = SmartDashboard.getNumber("Max Output", 0);
     double min = SmartDashboard.getNumber("Min Output", 0);
     double rotations = 2;
 
   /*  // if PID coefficients on SmartDashboard have changed, write new values to controller
     if((p != kP)) { m_pidController.setP(p); kP = p; }
     if((i != kI)) { m_pidController.setI(i); kI = i; }
     if((d != kD)) { m_pidController.setD(d); kD = d; }
     if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
     if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
     if((max != kMaxOutput) || (min != kMinOutput)) { 
       m_pidController.setOutputRange(min, max); 
      // kMinOutput = min; kMaxOutput = max; 
*/
     m_pidController.setReference(rotations, ControlType.kPosition);
     
     SmartDashboard.putNumber("SetPoint", rotations);
     SmartDashboard.putNumber("ProcessVariable", m_encoder.getPosition());
    
    }


public double getEncoderVal()
{
    return m_encoder.getPosition();
}


    public void run()
    {

    activatePositionalLoop();

    }
}
   


