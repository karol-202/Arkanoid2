package screens.game

import pl.karol202.uranium.core.common.UState
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.values.Vector

sealed class GameState : UState
{
	data class Prepare(override val paddleX: Double,
	                   override val bricks: List<Brick>) : GameState()
	{
		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)

		fun play(ballState: WCRigidbody.State) = Play(paddleX, bricks, ballState)
	}

	data class Play(override val paddleX: Double,
	                override val bricks: List<Brick>,
	                val ballState: WCRigidbody.State) : GameState()
	{
		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)

		fun withBrickHit(brickId: String) = copy(bricks = bricks.mapNotNull { if(it.id == brickId) it.hit() else it })

		fun withBallState(ballState: WCRigidbody.State) = copy(ballState = ballState)

		fun prepare() = Prepare(paddleX, bricks)
	}

	companion object
	{
		fun initial(size: Vector, bricks: List<Brick>): GameState = Prepare(size.x / 2.0, bricks)
	}

	abstract val paddleX: Double
	abstract val bricks: List<Brick>

	abstract fun withPaddleX(paddleX: Double): GameState
}
