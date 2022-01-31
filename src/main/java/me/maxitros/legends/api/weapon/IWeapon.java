package me.maxitros.legends.api.weapon;

public interface IWeapon {
    double GetModDamage();
    boolean CanRepair();
    double GetDefaultRepairCost();
    double GetDonateRepairCost();
}
