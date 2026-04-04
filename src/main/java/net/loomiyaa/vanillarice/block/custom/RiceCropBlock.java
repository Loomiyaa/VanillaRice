package net.loomiyaa.vanillarice.block.custom;

import net.loomiyaa.vanillarice.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class RiceCropBlock extends CropBlock implements Waterloggable
{
    public static final int MAX_AGE = 7;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public RiceCropBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
                .with(WATERLOGGED, false));
    }

    @Override
    protected ItemConvertible getSeedsItem()
    {
        return ModItems.RICE_SEEDS;
    }

    // our block is through-walkable and so pathfinding at all times
    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return true;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    // Make the block recognize the property, otherwise setting the property will throw exceptions.
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, WATERLOGGED);    // all properties need to be added here, so age too!
    }

    // make plantable on dirt and mud
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.DIRT) || floor.isOf(Blocks.MUD);
    }

    // make plantable only in water
    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getFluidState(pos).isOf(Fluids.WATER) &&
                world.getBlockState(pos.up()).isOf(Blocks.AIR) &&
                (
                        world.getBlockState(pos.down()).isOf(Blocks.DIRT) ||
                                world.getBlockState(pos.down()).isOf(Blocks.MUD)
                );
    }

    // instantly water log the crop if placed in water
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }

    // display crop with water in it, when waterlogged
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    // handle water flow through the crops
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    // handle what happens if the crop grows (aka preserve water source block)
    @Override
    public BlockState withAge(int age) {
        return (BlockState)this.getDefaultState()
                .with(this.getAgeProperty(), age)
                .with(WATERLOGGED, WATERLOGGED.getValues().get(0)); //append current waterlogged state to state applied after growth
    }

    // We override here only to lower light level (by 1) to 8 and also handle if moisture is 0.0f
    // Just a QOL thing for players I guess
    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (getAvailableMoisture(this, world, pos) == 0.0f)
            return;

        if (world.getBaseLightLevel(pos, 0) >= 8) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(i + 1), 2);
                }
            }
        }
    }

    // "Override" how crop calculates moisture.
    // We grow at full speed (7.0) if all conditions are met, else we don't grow at all (0.0).
    // CARE: vanilla blocks never return 0.0f as it breaks randomTick(...). We handle it here, but if another
    // mod would get that property for some off reason, this might lead to a bug
    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        // conditions are: generally waterlogged, planted on dirt/mud, and also specifically waterlogged with water
        // -> is waterlogged a duplication of isIn(water)? I don't know, let's be safe
        if (block.getStateWithProperties(world.getBlockState(pos)).get(WATERLOGGED, Boolean.FALSE)
                && (world.getBlockState(pos.down()).isOf(Blocks.DIRT) || world.getBlockState(pos.down()).isOf(Blocks.MUD))
                && world.getFluidState(pos).isIn(FluidTags.WATER)
        )
                return 7.0f;
        else
            return 0.0f;
    }
}
