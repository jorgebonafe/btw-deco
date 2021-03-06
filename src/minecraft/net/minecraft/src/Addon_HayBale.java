package net.minecraft.src;
public class Addon_HayBale
{
	public static Block hayBale;
	public Addon_HayBale()
	{
		hayBale = new BlockBale(3025);
		AddonManager.MakeStorage(Item.wheat, hayBale);
	}
	public static class BlockBale extends Block implements FCIBlock
	{
		public BlockBale(int id)
		{
			super(id, Material.cloth);
			setUnlocalizedName("blockHay");
			setStepSound(soundGrassFootstep);
			setCreativeTab(CreativeTabs.tabBlock);
			ItemAxe.SetAllAxesToBeEffectiveVsBlock(this);
			AddonManager.Register(this);
			AddonManager.Name(this, "Hay Bale");
		}
		public boolean isOpaqueCube()
		{
			return true;
		}
		public boolean renderAsNormalBlock()
		{
			return true;
		}
		public boolean canDropFromExplosion(Explosion var1)
		{
			return false;
		}
		public void onBlockDestroyedByExplosion(World world, int X, int Y, int Z, Explosion exp)
		{
			float v = 1.0F;

			if (exp != null)
			{
				v = 1.0F / exp.explosionSize;
			}

			for (int i=0;i<8;++i)
				if (world.rand.nextFloat()<=v)
					dropBlockAsItem_do(world, X, Y, Z, new ItemStack(Item.wheat));
		}
		
		public int GetFacing(IBlockAccess access, int X, int Y, int Z)
		{
			return access.getBlockMetadata(X,Y,Z);
		}
		public void SetFacing(World world, int X, int Y, int Z, int facing)
		{
			world.setBlockMetadataWithNotify(X,Y,Z,facing);
		}
		public int GetFacingFromMetadata(int meta)
		{
			return meta;
		}
		public int SetFacingInMetadata(int var1, int var2)
		{
			return var2;
		}
		public boolean CanRotateOnTurntable(IBlockAccess access, int X, int Y, int Z)
		{
			return access.getBlockMetadata(X,Y,Z)!=0;
		}
		public boolean CanTransmitRotationHorizontallyOnTurntable(IBlockAccess access, int X, int Y, int Z)
		{
			return true;
		}
		public boolean CanTransmitRotationVerticallyOnTurntable(IBlockAccess access, int X, int Y, int Z)
		{
			return true;
		}
		public void RotateAroundJAxis(World world, int X, int Y, int Z, boolean var5)
		{
			FCUtilsMisc.StandardRotateAroundJ(this, world, X, Y, Z, var5);
		}
		public int RotateMetadataAroundJAxis(int var1, boolean var2)
		{
			return FCUtilsMisc.StandardRotateMetadataAroundJ(this, var1, var2);
		}
		public boolean ToggleFacing(World world, int X, int Y, int Z, boolean var5)
		{
			this.RotateAroundJAxis(world, X, Y, Z, var5);
			return true;
		}
		public int onBlockPlaced(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8, int var9)
		{
			if(var5<2)return 0;
			else if(var5<4)return 1;
			else return 2;
		}
		public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5, ItemStack var6)
		{/*
			int var7 = FCUtilsMisc.ConvertPlacingEntityOrientationToBlockFacing(var5);
			
			if(var7<2)var7=0;
			else if(var7<4)var7=1;
			else var7=2;
			this.SetFacing(var1, var2, var3, var4, var7);//*/
		}
//CLIENT ONLY
		static Icon topIcon;
		public Icon getIcon(int side, int meta)
		{
			switch(meta)
			{
				case 0:
					return (side<2)?topIcon:blockIcon;
				case 1:
					return (side<4&&side>1)?topIcon:blockIcon;
				default:
					return (side>3)?topIcon:blockIcon;
			}
		}
		public void registerIcons(IconRegister r)
		{
			blockIcon = r.registerIcon("ginger_hay_side");
			topIcon = r.registerIcon("ginger_hay_top");
		}
		public boolean RenderBlock(RenderBlocks r, int X, int Y, int Z)
		{
			int f = GetFacing(r.blockAccess, X, Y, Z);
			switch(f)
			{
			case 1:
				r.SetUvRotateNorth(1);
				r.SetUvRotateSouth(1);
				break;
			case 2:
				r.SetUvRotateTop(1);
				r.SetUvRotateBottom(1);
				r.SetUvRotateWest(1);
				r.SetUvRotateEast(1);
			default:
				
			}
			r.setRenderBounds(0D,0D,0D,1D,1D,1D);
			r.renderStandardBlock(this, X, Y, Z);
			r.ClearUvRotation();
			return true;
		}
//
	}
}
