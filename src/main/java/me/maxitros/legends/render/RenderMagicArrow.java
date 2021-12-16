package me.maxitros.legends.render;

import me.maxitros.legends.Legends;
import me.maxitros.legends.entities.magic.EntityMagicArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderMagicArrow extends RenderArrow<EntityMagicArrow>
{
    public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");
    public static final ResourceLocation RES_TIPPED_ARROW = new ResourceLocation("textures/entity/projectiles/tipped_arrow.png");

    public RenderMagicArrow(RenderManager manager)
    {
        super(manager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMagicArrow entity) {
        return new ResourceLocation(Legends.modId, "textures/entity/projectiles/arrow.png");
    }


}
