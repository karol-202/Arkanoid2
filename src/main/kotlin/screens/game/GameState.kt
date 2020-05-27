package screens.game

import pl.karol202.uranium.core.common.UState
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.values.Vector

sealed class GameState : UState
{
	data class Prepare(override val paddleX: Double,
	                   override val bricks: List<Brick>,
	                   override val playerHp: Int) : GameState()
	{
		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)

		fun play(ballState: WCRigidbody.State) = Play(paddleX, bricks, playerHp, ballState)
	}

	data class Play(override val paddleX: Double,
	                override val bricks: List<Brick>,
	                override val playerHp: Int,
	                val ballState: WCRigidbody.State) : GameState()
	{
		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)

		fun withBrickHit(brickId: String) = copy(bricks = bricks.mapNotNull { if(it.id == brickId) it.hit() else it })

		fun withPlayerHpDecremented() = if(playerHp > 1) copy(playerHp = playerHp - 1) else null

		fun withBallState(ballState: WCRigidbody.State) = copy(ballState = ballState)

		fun prepare() = Prepare(paddleX, bricks, playerHp)

		fun gameOver() = GameOver(paddleX, bricks)
	}

	data class GameOver(override val paddleX: Double,
	                    override val bricks: List<Brick>) : GameState()
	{
		override val playerHp get() = 0

		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)
	}

	companion object
	{
		fun initial(size: Vector, bricks: List<Brick>, playerHp: Int): GameState = Prepare(size.x / 2.0, bricks, playerHp)
	}

	abstract val paddleX: Double
	abstract val bricks: List<Brick>
	abstract val playerHp: Int

	abstract fun withPaddleX(paddleX: Double): GameState
}
