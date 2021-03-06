package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;

public class Addon_Glass
{
	public static Block glassStained, glassPaneStained;
	public static Item glassChunk;
	public Addon_Glass()
	{
		glassChunk = new Item(30002).setUnlocalizedName("ginger_glassball").setCreativeTab(CreativeTabs.tabMaterials);
		glassStained = new BlockStainedGlass(3003);
		AddonManager.Name(glassChunk, "Piece of Glass");
		

		FCRecipes.AddStokedCrucibleRecipe(new ItemStack(Block.glass, 1), new ItemStack[] {new ItemStack(glassChunk, 4)});
		FCRecipes.AddStokedCrucibleRecipe(new ItemStack(glassChunk, 1), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcItemPileSand)});
		FCRecipes.AddShapelessVanillaRecipe(new ItemStack(glassChunk, 4), new Object[]{new ItemStack(Block.glass, 1)});

		FCRecipes.RemoveVanillaRecipe(new ItemStack(Block.thinGlass, 16), new Object[] {"###", "###", '#', Block.glass});
		FCRecipes.AddVanillaRecipe(new ItemStack(Block.thinGlass, 12), new Object[] {"###", "###", '#', Block.glass});

		FCCraftingManagerCrucibleStoked.getInstance().RemoveRecipe(new ItemStack(Block.glass, 3), new ItemStack[] {new ItemStack(Block.thinGlass, 8)});
		FCRecipes.AddStokedCrucibleRecipe(new ItemStack(Block.glass, 1), new ItemStack[] {new ItemStack(Block.thinGlass, 2)});

		FCRecipes.RemoveVanillaRecipe(new ItemStack(Item.glassBottle, 3), new Object[] {"# #", " # ", '#', Block.glass});
		FCRecipes.AddVanillaRecipe(new ItemStack(Item.glassBottle, 3), new Object[] {" # ", "# #", "###", '#', glassChunk});
		FCRecipes.AddStokedCrucibleRecipe(new ItemStack(glassChunk, 2), new ItemStack[] {new ItemStack(Item.glassBottle, 1)});
	}
	public static class BlockStainedGlass extends FCBlockGlass
	{
		public BlockStainedGlass(int ID)
		{
			super(ID,Material.glass,false);
			setCreativeTab((CreativeTabs)null);
			setHardness(0.3F);
			setStepSound(soundGlassFootstep);
			setUnlocalizedName("ginger_glass_");
			AddonManager.Register(this, new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "lightGrey", "grey", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" }, new String[] { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "Light Grey", "Grey", "Pink", "Lime", "Yellow", "Light Blue", "Magenta", "Orange", "White" }, " Stained Glass Block");
		}
		
		@Override
		public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving,
				ItemStack par6ItemStack) {
			
			if (par5EntityLiving instanceof EntityPlayer) {
				EntityPlayer ep = (EntityPlayer)par5EntityLiving;
				if (ep.inventory.getCurrentItem().itemID != 3003)
					return;				
			}
			
			sendClientOldGlassBlockMessage(par6ItemStack.stackSize-1, par6ItemStack.getItemDamage());
			
			super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
		}
		
		public void sendClientOldGlassBlockMessage(int size, int damage) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(baos);
				dos.writeInt(size);
				dos.writeInt(damage);
				Packet250CustomPayload packet = new Packet250CustomPayload("DECO|OLDGLASS", baos.toByteArray());
				Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public int damageDropped(int Meta)
		{
			return Meta;
		}
		public int getRenderBlockPass()
		{
			return 1;
		}
//CLIENT ONLY METHODS
		public static Icon[] PaneSideIcons = new Icon[16], Icons = new Icon[16];
		public Icon getIcon(int Side, int Meta)
		{
			return Icons[Meta];
		}
		public void registerIcons(IconRegister Register)
		{
			for (int Index = 0; Index < 16; Index++)
			{
				Icons[Index] = Register.registerIcon("ginger_glass_" + Index);
				PaneSideIcons[Index] = Register.registerIcon("ginger_glass_pane_top_" + Index);
			}
		}
//
	}
}