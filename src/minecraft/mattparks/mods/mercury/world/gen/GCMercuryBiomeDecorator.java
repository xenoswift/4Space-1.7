package mattparks.mods.mercury.world.gen;

import java.util.Random;

import mattparks.mods.mercury.blocks.MercuryBlocks;
import micdoodle8.mods.galacticraft.api.event.wgen.GCCoreEventPopulate;
import micdoodle8.mods.galacticraft.core.world.gen.GCCoreWorldGenMinableMeta;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;

public class GCMercuryBiomeDecorator
{
	protected World currentWorld;
	protected Random rand;

	protected int chunkX;
	protected int chunkZ;

    protected WorldGenerator dirtGen;
    protected WorldGenerator iridiumGen;
    protected WorldGenerator tinGen;
    protected WorldGenerator copperGen;
    protected WorldGenerator meteorGen;
    protected WorldGenerator goldGen;

	public GCMercuryBiomeDecorator()
	{
        this.dirtGen = new GCCoreWorldGenMinableMeta(MercuryBlocks.MercuryDirt.blockID, 25, 6, false, MercuryBlocks.MercuryStone.blockID, 9); 
        this.tinGen = new GCCoreWorldGenMinableMeta(MercuryBlocks.MercuryTinOre.blockID, 4, 1, false, MercuryBlocks.MercuryStone.blockID, 9);  
        this.copperGen = new GCCoreWorldGenMinableMeta(MercuryBlocks.MercuryCopperOre.blockID, 5, 3, false, MercuryBlocks.MercuryStone.blockID, 9);      
        this.iridiumGen = new GCCoreWorldGenMinableMeta(MercuryBlocks.MercuryIridiumOre.blockID, 8, 5, false, MercuryBlocks.MercuryStone.blockID, 9);
        this.meteorGen = new GCCoreWorldGenMinableMeta(MercuryBlocks.MercuryMeteorOre.blockID, 4, 3, false, MercuryBlocks.MercuryStone.blockID, 9);
        this.goldGen = new GCCoreWorldGenMinableMeta(MercuryBlocks.MercuryGoldOre.blockID, 7, 4, false, MercuryBlocks.MercuryStone.blockID, 9);
	}

	public void decorate(World world, Random random, int chunkX, int chunkZ)
	{
		if (this.currentWorld != null)
		{
			throw new RuntimeException("Already decorating!!");
		}
		else
		{
			this.currentWorld = world;
			this.rand = random;
			this.chunkX = chunkX;
			this.chunkZ = chunkZ;
			this.generateMercury();
			this.currentWorld = null;
			this.rand = null;
		}
	}

	protected void generateOre(int amountPerChunk, WorldGenerator worldGenerator, int minY, int maxY)
	{
		for (int var5 = 0; var5 < amountPerChunk; ++var5)
		{
			final int var6 = this.chunkX + this.rand.nextInt(16);
			final int var7 = this.rand.nextInt(maxY - minY) + minY;
			final int var8 = this.chunkZ + this.rand.nextInt(16);
			worldGenerator.generate(this.currentWorld, this.rand, var6, var7, var8);
		}
	}

	protected void generateMercury()
	{
		MinecraftForge.EVENT_BUS.post(new GCCoreEventPopulate.Pre(this.currentWorld, this.rand, this.chunkX, this.chunkZ));
        this.generateOre(20, this.dirtGen, 0, 150);
        this.generateOre(35, this.tinGen, 0, 60);
        this.generateOre(36, this.copperGen, 0, 60);
        this.generateOre(26, this.iridiumGen, 0, 20);
        this.generateOre(12, this.meteorGen, 0, 40);
        this.generateOre(36, this.goldGen, 0, 30);
		MinecraftForge.EVENT_BUS.post(new GCCoreEventPopulate.Post(this.currentWorld, this.rand, this.chunkX, this.chunkZ));
	}
}