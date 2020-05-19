package screens.game

import pl.karol202.uranium.core.common.UState
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.values.Vector

sealed class GameState : UState
{
	data class Prepare(override val paddleX: Double) : GameState()
	{
		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)

		fun play(ballState: WCRigidbody.State) = Play(paddleX, ballState)
	}

	data class Play(override val paddleX: Double,
	                val ballState: WCRigidbody.State) : GameState()
	{
		override fun withPaddleX(paddleX: Double) = copy(paddleX = paddleX)

		fun withBallState(ballState: WCRigidbody.State) = copy(ballState = ballState)

		fun prepare() = Prepare(paddleX)
	}

	companion object
	{
		fun initial(size: Vector): GameState = Prepare(size.x / 2.0)
	}

	abstract val paddleX: Double

	abstract fun withPaddleX(paddleX: Double): GameState
}
