package net.loomiyaa.vanillarice.block.custom;

import net.loomiyaa.vanillarice.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class RiceCropBlock extends CropBlock implements SimpleWaterloggedBlock
{
    public static final int MAX_AGE = 7;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RiceCropBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState()
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected ItemLike getBaseSeedId()
    {
        return ModItems.RICE_SEEDS;
    }

    // our block is through-walkable and so pathfinding at all times
    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return true;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    // Make the block recognize the property, otherwise setting the property will throw exceptions.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, WATERLOGGED);    // all properties need to be added here, so age too!
    }

    // make plantable on dirt and mud
    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(Blocks.DIRT) || floor.is(Blocks.MUD);
    }

    // make plantable only in water
    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getFluidState(pos).is(Fluids.WATER) &&
                world.getBlockState(pos.above()).is(Blocks.AIR) &&
                (
                        world.getBlockState(pos.below()).is(Blocks.DIRT) ||
                                world.getBlockState(pos.below()).is(Blocks.MUD)
                );
    }

    // instantly water log the crop if placed in water
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).is(Fluids.WATER));
    }

    // display crop with water in it, when waterlogged
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // handle water flow through the crops
    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    // handle what happens if the crop grows (aka preserve water source block)
    @Override
    public BlockState getStateForAge(int age) {
        return (BlockState)this.defaultBlockState()
                .setValue(this.getAgeProperty(), age)
                .setValue(WATERLOGGED, WATERLOGGED.getPossibleValues().get(0)); //append current waterlogged state to state applied after growth
    }

    // We override here only to lower light level (by 1) to 8 and also handle if moisture is 0.0f
    // Just a QOL thing for players I guess
    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (getGrowthSpeed(this, world, pos) == 0.0f)
            return;

        if (world.getRawBrightness(pos, 0) >= 8) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    world.setBlock(pos, this.getStateForAge(i + 1), 2);
                }
            }
        }
    }

    // "Override" how crop calculates moisture.
    // We grow at full speed (7.0) if all conditions are met, else we don't grow at all (0.0).
    // CARE: vanilla blocks never return 0.0f as it breaks randomTick(...). We handle it here, but if another
    // mod would get that property for some off reason, this might lead to a bug
    protected static float getGrowthSpeed(Block block, BlockGetter world, BlockPos pos) {
        // conditions are: generally waterlogged, planted on dirt/mud, and also specifically waterlogged with water
        // -> is waterlogged a duplication of isIn(water)? I don't know, let's be safe
        if (block.withPropertiesOf(world.getBlockState(pos)).getValueOrElse(WATERLOGGED, Boolean.FALSE)
                && (world.getBlockState(pos.below()).is(Blocks.DIRT) || world.getBlockState(pos.below()).is(Blocks.MUD))
                && world.getFluidState(pos).is(FluidTags.WATER)
        )
                return 7.0f;
        else
            return 0.0f;
    }
}
