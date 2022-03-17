package me.maxitros.legends.capabilities;

public interface IMobLvl {
    float GetHp();
    int GetLvl();
    void SetHp(float hp);
    void SetLvl(int lvl);
    void SetDefault(boolean val);
    boolean IsDefault();
}
