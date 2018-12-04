package org.usfirst.frc.team1251.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1251.robot.RobotMap;
import org.usfirst.frc.team1251.robot.commands.DeferredCmdSupplier;
import org.usfirst.frc.team1251.robot.commands.MoveBigMotor;
import org.usfirst.frc.team1251.robot.teleopInput.driverInput.HumanInput;

public class BigMotor extends Subsystem{

    private final DeferredCmdSupplier<Command> defaultCmdSupplier;
    private DoubleSolenoid clawSolenoid;
    private HumanInput input;
    private MoveBigMotor moveBigMotor;

    private static final boolean isMotor1Inverted = false;
    private static final boolean isMotor2Inverted = false;
    private final double SUSTAIN = 0.13;


    // Motor(s) for elevators (move it up and down)
    private Victor bigMotor1;
    private Victor bigMotor2;

    public BigMotor(DeferredCmdSupplier<Command> defaultCmdSupplier, HumanInput humanInput, MoveBigMotor bigMotor){
        this.defaultCmdSupplier = defaultCmdSupplier;
        clawSolenoid = new DoubleSolenoid(RobotMap.CLAW_SOLENOID_FORWARD, RobotMap.CLAW_SOLENOID_REVERSE);
        this.input = humanInput;
        this.moveBigMotor = bigMotor;
        bigMotor1 = new Victor(RobotMap.BIG_MOTOR_1);
        bigMotor2 = new Victor(RobotMap.BIG_MOTOR_2);


    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(this.defaultCmdSupplier.get());
    }

    public void forward(double speed) {
        bigMotor1.set(SUSTAIN);
        bigMotor2.set(SUSTAIN);

        //speed between 0 and 1
        speed = Math.max(speed, 0);
        //speed = Math.min(speed, 1);

        bigMotor1.set(speed);
        bigMotor2.set(speed);
    }
    public void reverse(double speed) {
        speed = Math.max(speed, 0);
        speed = Math.min(speed, 0.8);
        speed *= -1;

        bigMotor1.set(speed);
        bigMotor2.set(speed);
    }

    public void move(double speed) {
        bigMotor1.set(speed);
        bigMotor2.set(speed);
    }
    public void stop() {
        bigMotor1.set(0);
        bigMotor2.set(0);
    }

    //Pistons set to default state and opens
    public void openClaw (){
        clawSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    //Pistons extend to close claw
    public void closeClaw () {
        clawSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

}
