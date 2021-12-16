package me.maxitros.legends.render;

import me.maxitros.legends.entities.magic.EntityIceCube;
import me.maxitros.legends.entities.magic.EntityMine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class RenderMine<E extends Entity> extends Render<EntityMine>
{
    public RenderMine(RenderManager renderManagerIn, RenderItem renderer)
    {
        super(renderManagerIn);
        this.itemRenderer = renderer;
    }
    private final RenderItem itemRenderer;

    final ItemStack stack = new ItemStack(Items.SNOWBALL);
    public void doRender(EntityMine entity, double x, double y, double z, float entityYaw, float partialTicks)
    {

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.GROUND);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

    }

    protected ResourceLocation getEntityTexture(EntityMine entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
