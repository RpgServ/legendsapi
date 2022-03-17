package me.maxitros.legends.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow public abstract void fall(float distance, float damageMultiplier);

    @Shadow public abstract UUID getPersistentID();

    @Shadow public abstract String getCachedUniqueIdString();

    @Shadow public abstract UUID getUniqueID();

    @Shadow public World world;
}
