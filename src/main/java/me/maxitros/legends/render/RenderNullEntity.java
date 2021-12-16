package me.maxitros.legends.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderNullEntity <T extends Entity> extends Render<T> {
    public RenderNullEntity(RenderManager renderManager) {
        super(renderManager);
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}
