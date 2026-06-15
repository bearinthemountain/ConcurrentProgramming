package org.example.Exercices.CarsSingleton;

public class CarSensorSingletonLauncher
{ public CarSensorSingletonLauncher() { }
    public static void main(String[] args)
    { CarSensorSingletonLauncher launcher =
            new CarSensorSingletonLauncher();
        launcher.launch(); }

    public void launch() {

    DbleCheckedLockingSingletonSensorsManager monManagerUnique = DbleCheckedLockingSingletonSensorsManager.getInstance();

    this.test(monManagerUnique);

}
    private void test(DbleCheckedLockingSingletonSensorsManager manager)
    { manager.addSensor(new TemperatureSensor());
        manager.addSensor(new SpeedSensor());
    manager.addSensor(new RoadConditionSensor());
    manager.printState(); } }