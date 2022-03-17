package me.maxitros.legends.capabilities;

public class MobLvl implements IMobLvl{

    protected int lvl;
    protected float hp;
    protected boolean isDefault;

    public MobLvl(int lvl, float hp, boolean isDefault)
    {
        this.lvl = lvl;
        this.hp = hp;
        this.isDefault = true;
    }

    @Override
    public float GetHp() {
        return hp;
    }

    @Override
    public int GetLvl() {
        return lvl;
    }

    @Override
    public void SetHp(float hp) {
        this.hp = hp;
    }

    @Override
    public void SetLvl(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public void SetDefault(boolean val) {
        isDefault = val;
    }

    @Override
    public boolean IsDefault() {
        return isDefault;
    }
}
